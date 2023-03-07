package com.etu.booking.viewmodel

import android.util.Log
import com.etu.booking.data.entity.HistoryEntity
import com.etu.booking.data.repository.HistoryRepository
import com.etu.booking.data.repository.HotelRepository
import com.etu.booking.data.repository.LocationRepository
import com.etu.booking.mapper.toCardModel
import com.etu.booking.mapper.toHistoryEntity
import com.etu.booking.mapper.toModel
import com.etu.booking.model.BookingHotelModel
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.BookingStatus.BOOKED
import com.etu.booking.model.HotelCardModel
import com.etu.booking.model.LocationModel
import com.etu.booking.model.filter.BookingSearchFilter
import com.etu.booking.utils.compareBy
import com.etu.booking.utils.next
import com.etu.booking.utils.thenBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class BookingSearchViewModel(
    private val hotelRepository: HotelRepository,
    private val locationRepository: LocationRepository,
    private val historyRepository: HistoryRepository,
) : ViewModelWithLoading() {

    private val _booking = MutableStateFlow(BookingSearchModel())
    private val _filter = MutableStateFlow(BookingSearchFilter())
    private val _hotels = MutableStateFlow(emptyList<HotelCardModel>())
    private val _locations = MutableStateFlow(emptyList<LocationModel>())
    private val _isSuccessfullyBooked = MutableStateFlow(false)

    val booking = _booking.asStateFlow()
    val filter = _filter.asStateFlow()
    val hotels = _hotels.asStateFlow()
    val locations = _locations.asStateFlow()
    val isSuccessfullyBooked = _isSuccessfullyBooked.asStateFlow()

    private val locationPattern = Regex("[A-Za-z- ]{0,30}")

    fun setLocation(location: LocationModel) {
        launchWithLoading {
            _locations.update {
                locationRepository.findAllByCityAndCountryRegex(
                    city = if (location.city != " ") location.city else "",
                    country = location.country,
                )
                    .firstOrNull()
                    ?.map { it.toModel() } ?: emptyList()
            }
        }
        _booking.update {
            it.copy(
                location = location,
                errorModel = it.errorModel.copy(
                    location = !(locationPattern.matches(location.country)
                            && locationPattern.matches(location.city)
                            && location.country.trim() != ""
                            && location.city.trim() != "")
                )
            )
        }
    }

    fun setCheckIn(checkIn: LocalDate) {
        val now = LocalDate.now()
        _booking.update {
            val isCorrect = now > checkIn || isCheckInValid(checkIn, it.checkOut)
            it.copy(
                checkIn = checkIn,
                errorModel = it.errorModel.copy(
                    checkIn = isCorrect,
                    checkOut = (it.checkIn != null && now > it.checkOut) || isCorrect,
                )
            )
        }
    }

    fun setCheckOut(checkOut: LocalDate) {
        val now = LocalDate.now()
        _booking.update {
            val isCorrect = now > checkOut || isCheckOutValid(it.checkIn, checkOut)
            it.copy(
                checkOut = checkOut,
                errorModel = it.errorModel.copy(
                    checkOut = isCorrect,
                    checkIn = (it.checkIn != null && now > it.checkIn) || isCorrect,
                )
            )
        }
    }

    fun setMinPriceRepNight(minPricePerNight: Int?) {
        _booking.update {
            it.copy(
                minPricePerNight = minPricePerNight,
                errorModel = it.errorModel.copy(
                    minPricePerNight = !(minPricePerNight == null || minPricePerNight > 0)
                )
            )
        }
    }

    fun setMaxPriceRepNight(maxPricePerNight: Int?) {
        _booking.update {
            it.copy(
                maxPricePerNight = maxPricePerNight,
                errorModel = it.errorModel.copy(
                    maxPricePerNight = !(maxPricePerNight == null || maxPricePerNight > 0)
                )
            )
        }
    }

    fun setMaxDistanceToCenterInKm(maxDistanceToCenterInKm: Int?) {
        _booking.update {
            it.copy(
                maxDistanceToCenterInKm = maxDistanceToCenterInKm,
                errorModel = it.errorModel.copy(
                    maxDistanceToCenterInKm =
                    !(maxDistanceToCenterInKm == null || maxDistanceToCenterInKm >= 0)
                )
            )
        }
    }

    fun setGuestAmount(guestsAmount: Int?) {
        _booking.update {
            it.copy(
                guestsAmount = guestsAmount,
                errorModel = it.errorModel.copy(
                    guestsAmount = !(guestsAmount == null || (guestsAmount in 1..10))
                )
            )
        }
    }

    fun updateHotels(bookingSearchModel: BookingSearchModel) = launchWithLoading {
        _hotels.update {
            hotelRepository.findAllByFilters(
                city = bookingSearchModel.location?.city ?: "",
                country = bookingSearchModel.location?.country ?: "",
                start = bookingSearchModel.checkIn?.let { dateFormat.format(it) } ?: "",
                end = bookingSearchModel.checkOut?.let { dateFormat.format(it) } ?: "",
                minPrice = bookingSearchModel.minPricePerNight ?: Int.MIN_VALUE,
                maxPrice = bookingSearchModel.maxPricePerNight ?: Int.MAX_VALUE,
                maxDistance = bookingSearchModel.maxDistanceToCenterInKm ?: Int.MAX_VALUE,
                guestCount = bookingSearchModel.guestsAmount ?: Int.MIN_VALUE,
            )
            .firstOrNull()
            ?.map { it.toCardModel() }
            ?.applyFilter(_filter.value) ?: emptyList()
        }
    }

    private var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private fun List<HotelCardModel>.applyFilter(
        filter: BookingSearchFilter,
    ): List<HotelCardModel> {
        val comparator = compareBy<HotelCardModel>(filter.price) { it.pricePerNight }
            .thenBy(filter.rating) { it.score }
            .thenBy(filter.distance) { it.kmFromCenter }

        return sortedWith(comparator)
    }

    fun book(id: UUID) = launchWithLoading {
        _isSuccessfullyBooked.update {
            try {
                bookHotel(id)
                true
            } catch(ex: Exception) {
                Log.e("blz", "${ex.message}")
                false
            }
        }
    }

    private suspend fun bookHotel(id: UUID) {
        val hotel = hotelRepository.findById(id.toString()).firstOrNull()
        val history = BookingHotelModel(
            hotelId = id,
            personId = UUID.fromString("f02cc00b-9127-4214-9450-b561615b7511"),
            checkIn = booking.value.checkIn!!,
            checkOut = booking.value.checkOut!!,
            fullPrice = Period.between(booking.value.checkIn, booking.value.checkOut).plusDays(1).days * hotel!!.pricePerNight,
            currency = hotel.currency,
            status = BOOKED,
        ).toHistoryEntity()
        assertNoDuplicateHistory(history = history)
        historyRepository.insert(history)
    }

    private fun assertNoDuplicateHistory(history: HistoryEntity) = history.let {
        if (historyRepository.existByHotelIdAndPersonIdAndDates(
                hotelId = it.hotelId,
                personId = it.personId,
                checkIn = it.checkIn,
                checkOut = it.checkOut,
            )
        ) {
            throw IllegalAccessException("Duplicate history exception")
        }
    }

    fun nextPriceSorting(bookingSearchModel: BookingSearchModel) = launchWithLoading {
        _filter.update { it.copy(price = it.price.next()) }
        updateHotels(bookingSearchModel = bookingSearchModel).join()
    }

    fun nextRatingSorting(bookingSearchModel: BookingSearchModel) = launchWithLoading {
        _filter.update { it.copy(rating = it.rating.next()) }
        updateHotels(bookingSearchModel = bookingSearchModel).join()
    }

    fun nextDistanceSorting(bookingSearchModel: BookingSearchModel) = launchWithLoading {
        _filter.update { it.copy(distance = it.distance.next()) }
        updateHotels(bookingSearchModel = bookingSearchModel).join()
    }

    fun highlightInputs() {
        _booking.update {
            val locationValue = LocationModel.print(_booking.value.location)
            val checkInValue = _booking.value.checkIn
            val checkOutValue = _booking.value.checkOut
            val guestsAmountValue = _booking.value.guestsAmount

            it.copy(
                errorModel = it.errorModel.copy(
                    location = if (locationValue.isEmpty()) true else it.errorModel.location,
                    checkIn = if (checkInValue == null) true else it.errorModel.checkIn,
                    checkOut = if (checkOutValue == null) true else it.errorModel.checkOut,
                    guestsAmount = if (guestsAmountValue == null) true else it.errorModel.guestsAmount
                )
            )
        }
    }

    private fun isCheckInValid(checkIn: LocalDate, checkOut: LocalDate?) =
        checkOut != null && checkIn > checkOut

    private fun isCheckOutValid(checkIn: LocalDate?, checkOut: LocalDate) =
        checkIn != null && checkOut < checkIn
}
