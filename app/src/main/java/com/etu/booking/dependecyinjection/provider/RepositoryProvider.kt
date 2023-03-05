package com.etu.booking.dependecyinjection.provider

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.etu.booking.viewmodel.HotelViewModel

object RepositoryProvider {

    val Factory = viewModelFactory {
        initializer {
            HotelViewModel(
                hotelRepository = bookingApplication().repositoryContext.hotelRepository
            )
        }
    }
}