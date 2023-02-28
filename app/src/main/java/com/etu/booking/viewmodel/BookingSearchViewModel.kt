package com.etu.booking.viewmodel

import com.etu.booking.default.DefaultModels
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.HotelCardModel
import com.etu.booking.model.LocationModel
import com.etu.booking.model.filter.BookingSearchFilter
import com.etu.booking.utils.compareBy
import com.etu.booking.utils.next
import com.etu.booking.utils.thenBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

class BookingSearchViewModel : ViewModelWithLoading() {

    private val _booking = MutableStateFlow(BookingSearchModel())
    private val _filter = MutableStateFlow(BookingSearchFilter())
    private val _hotels = MutableStateFlow(emptyList<HotelCardModel>())
    private val _isSuccessfullyBooked = MutableStateFlow(false)

    val booking = _booking.asStateFlow()
    val filter = _filter.asStateFlow()
    val hotels = _hotels.asStateFlow()
    val isSuccessfullyBooked = _isSuccessfullyBooked.asStateFlow()

    private val locationPattern = Regex("[A-Za-z- ]{0,30}")

    fun setLocation(location: LocationModel) {
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

    fun updateHotels() = launchWithLoading {
        _hotels.update { DefaultModels.HOTEL_CARDS_MODELS.applyFilter(_filter.value) }  // TODO: change to a repository call
    }

    private fun List<HotelCardModel>.applyFilter(
        filter: BookingSearchFilter,
    ): List<HotelCardModel> {
        val comparator = compareBy<HotelCardModel>(filter.price) { it.pricePerNight }
            .thenBy(filter.rating) { it.score }
            .thenBy(filter.distance) { it.kmFromCenter }

        return sortedWith(comparator)
    }

    fun book(id: UUID) = launchWithLoading {
        _isSuccessfullyBooked.update { Random.nextBoolean() } // TODO: change to a repository call
    }

    fun nextPriceSorting() = launchWithLoading {
        _filter.update { it.copy(price = it.price.next()) }
        updateHotels()
    }

    fun nextRatingSorting() = launchWithLoading {
        _filter.update { it.copy(rating = it.rating.next()) }
        updateHotels()
    }

    fun nextDistanceSorting() = launchWithLoading {
        _filter.update { it.copy(distance = it.distance.next()) }
        updateHotels()
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
