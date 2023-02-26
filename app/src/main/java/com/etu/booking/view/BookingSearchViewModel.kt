package com.etu.booking.view

import androidx.lifecycle.ViewModel
import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.LocationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class BookingSearchViewModel(
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private var _booking = MutableStateFlow(BookingSearchModel())

    val booking: StateFlow<BookingSearchModel> = _booking.asStateFlow()
    
    private val locationPattern = Regex("[A-Za-z-]{1,30}")

    fun setLocation(location: LocationModel) {
        _booking.update { it.copy(
            location = location,
            errorModel = it.errorModel.copy(
                location = !(locationPattern.matches(location.country)
                        && locationPattern.matches(location.city))
            )
        ) }
    }

    fun setCheckIn(checkIn: LocalDate) {
        val now = LocalDate.now()
        _booking.update { it.copy(
            checkIn = checkIn,
            errorModel = it.errorModel.copy(
                checkIn = now > checkIn
            )
        ) }
    }

    fun setCheckOut(checkOut: LocalDate) {
        val now = LocalDate.now()
        _booking.update { it.copy(
            checkOut = checkOut,
            errorModel = it.errorModel.copy(
                checkOut = now > checkOut
            )
        ) }
    }

    fun setMinPriceRepNight(minPricePerNight: Int?) {
        _booking.update { it.copy(
            minPricePerNight = minPricePerNight,
            errorModel = it.errorModel.copy(
                minPricePerNight = !(minPricePerNight == null || minPricePerNight > 0)
            )
        ) }
    }

    fun setMaxPriceRepNight(maxPricePerNight: Int?) {
        _booking.update { it.copy(
            maxPricePerNight = maxPricePerNight,
            errorModel = it.errorModel.copy(
                maxPricePerNight = !(maxPricePerNight == null || maxPricePerNight > 0)
            )
        ) }
    }

    fun setMaxDistanceToCenterInKm(maxDistanceToCenterInKm: Int?) {
        _booking.update { it.copy(
            maxDistanceToCenterInKm = maxDistanceToCenterInKm,
            errorModel = it.errorModel.copy(
                maxDistanceToCenterInKm =
                    !(maxDistanceToCenterInKm == null || maxDistanceToCenterInKm >= 0)
            )
        ) }
    }

    fun setGuestAmount(guestsAmount: Int?) {
        _booking.update { it.copy(
            guestsAmount = guestsAmount,
            errorModel = it.errorModel.copy(
                guestsAmount = !(guestsAmount == null || (guestsAmount in 1..10))
            )
        ) }
    }
}
