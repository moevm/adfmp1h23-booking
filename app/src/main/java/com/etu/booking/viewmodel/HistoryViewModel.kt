package com.etu.booking.viewmodel

import com.etu.booking.data.repository.HistoryRepository
import com.etu.booking.data.repository.HotelRepository
import com.etu.booking.dependecyinjection.provider.CredentialProvider.getId
import com.etu.booking.mapper.toModel
import com.etu.booking.model.BookingStatus
import com.etu.booking.model.HistoryHotelModel
import com.etu.booking.model.filter.HistoryFilter
import com.etu.booking.utils.compareBy
import com.etu.booking.utils.getEnumIgnoreCase
import com.etu.booking.utils.next
import com.etu.booking.utils.thenBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update

class HistoryViewModel(
    private val historyRepository: HistoryRepository,
    private val hotelRepository: HotelRepository,
) : ViewModelWithLoading() {

    private val _hotels = MutableStateFlow(emptyList<HistoryHotelModel>())
    private val _filter = MutableStateFlow(HistoryFilter())

    val hotels = _hotels.asStateFlow()
    val filter = _filter.asStateFlow()

    fun updateHotels() = launchWithLoading {
        _hotels.update {
            getId()?.let { historyRepository.findAllByPersonId(it.toString())
                .firstOrNull()
                ?.map {
                    val hotel = hotelRepository.findById(it.hotelId).firstOrNull()
                    it.toModel(name = hotel?.name ?: "", address = hotel?.address ?: "")
                }
                ?.applyFilter(_filter.value) } ?: emptyList()
        }
    }

    private fun List<HistoryHotelModel>.applyFilter(
        filter: HistoryFilter
    ): List<HistoryHotelModel> {
        val comparator = compareBy<HistoryHotelModel>(filter.checkIn) { it.checkIn }
            .thenBy(filter.checkOut) { it.checkOut }

        return filter { filter.bookingStatus == null || it.status == filter.bookingStatus }
            .sortedWith(comparator)
    }

    fun nextCheckInSorting() = launchWithLoading {
        _filter.update { it.copy(checkIn = it.checkIn.next()) }
        updateHotels().join()
    }

    fun nextCheckOutSorting() = launchWithLoading {
        _filter.update { it.copy(checkOut = it.checkOut.next()) }
        updateHotels().join()
    }


    // ToDo check what wrong
    fun updateBookingStatusSorting(bookingStatus: String) = launchWithLoading {
        _filter.update {
            val newValue = getEnumIgnoreCase<BookingStatus>(bookingStatus)
            it.copy(bookingStatus = newValue)
        }
        updateHotels().join()
    }
}
