package com.etu.booking.viewmodel

import com.etu.booking.data.repository.HotelRepository
import com.etu.booking.mapper.toModel
import com.etu.booking.model.HotelModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import java.util.*

class HotelViewModel(
    private val hotelRepository: HotelRepository,
) : ViewModelWithLoading() {

    private val _hotel = MutableStateFlow<HotelModel?>(null)

    val hotel = _hotel.asStateFlow()

    fun updateHotel(hotelId: UUID) = launchWithLoading {
        _hotel.update {
            hotelRepository.findExtendedById(hotelId.toString())
                .firstOrNull()?.map { it.key.toModel(it.value) }?.firstOrNull()
        }
    }
}