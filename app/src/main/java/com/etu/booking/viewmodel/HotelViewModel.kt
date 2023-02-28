package com.etu.booking.viewmodel

import com.etu.booking.default.DefaultModels
import com.etu.booking.model.HotelModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*

class HotelViewModel : ViewModelWithLoading() {

    private val _hotel = MutableStateFlow<HotelModel?>(null)

    val hotel = _hotel.asStateFlow()

    fun updateHotel(hotelId: UUID) = launchWithLoading {
        _hotel.update {
            DefaultModels.HOTEL_MODELS
                .first { it.id == hotelId }
        }
    }
}