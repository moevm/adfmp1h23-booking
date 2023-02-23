package com.etu.booking.view

import androidx.lifecycle.ViewModel
import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.LocationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class BookingSearchViewModel(
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private var _booking = MutableStateFlow(BookingSearchModel())

    val booking: StateFlow<BookingSearchModel> = _booking.asStateFlow()

    fun setLocation(location: LocationModel) {
        _booking.value = _booking.value.copy(location = location)
    }

    fun setCheckIn(checkIn: LocalDate) {
        _booking.value = _booking.value.copy(checkIn = checkIn)
    }

    fun setCheckOut(checkOut: LocalDate) {
        _booking.value = _booking.value.copy(checkOut = checkOut)
    }

    fun setMinPriceRepNight(minPriceRepNight: Int) {
        _booking.value = _booking.value.copy(minPricePerNight = minPriceRepNight)
    }

    fun setMaxPriceRepNight(maxPriceRepNight: Int) {
        _booking.value = _booking.value.copy(maxPricePerNight = maxPriceRepNight)
    }

    fun setMaxDistanceToCenterInKm(maxDistanceToCenterInKm: Int) {
        _booking.value = _booking.value.copy(maxDistanceToCenterInKm = maxDistanceToCenterInKm)
    }

    fun setGuestAmount(guestsAmount: Int) {
        _booking.value = _booking.value.copy(guestsAmount = guestsAmount)
    }
}
