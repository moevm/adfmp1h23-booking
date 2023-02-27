package com.etu.booking.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.LocationModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

class BookingSearchViewModel(
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _booking = MutableStateFlow(BookingSearchModel())
    private val _isSuccessfullyBooked = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val booking: StateFlow<BookingSearchModel> = _booking.asStateFlow()
    val isSuccessfullyBooked: StateFlow<Boolean> = _isSuccessfullyBooked.asStateFlow()

    private val locationPattern = Regex("[A-Za-z- ]{0,30}")

    fun setLocation(location: LocationModel) {
        _booking.update { it.copy(
            location = location,
            errorModel = it.errorModel.copy(
                location = !(locationPattern.matches(location.country)
                        && locationPattern.matches(location.city)
                        && location.country.trim() != ""
                        && location.city.trim() != "")
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

    fun book(id: String) = launchWithLoading {
        _isSuccessfullyBooked.update { Random.nextBoolean() } // TODO: change to a repository call
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

    private fun launchWithLoading(block: suspend () -> Unit) = viewModelScope.launch {
        _isLoading.update { true }
        delay(1000) // TODO: Only for request delay emulation. It will be removed
        block()
        _isLoading.update { false }
    }
}
