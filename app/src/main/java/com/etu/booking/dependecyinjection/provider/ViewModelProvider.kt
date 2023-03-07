package com.etu.booking.dependecyinjection.provider

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.viewmodel.AuthViewModel
import com.etu.booking.viewmodel.AuthorizationViewModel
import com.etu.booking.viewmodel.BookingSearchViewModel
import com.etu.booking.viewmodel.DocumentViewModel
import com.etu.booking.viewmodel.HistoryViewModel
import com.etu.booking.viewmodel.HotelViewModel
import com.etu.booking.viewmodel.ProfileViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer { AuthorizationViewModel(SnackbarManager) }
        initializer { AuthViewModel() }
        initializer {
            BookingSearchViewModel(
                historyRepository = bookingApplication().repositoryContext.historyRepository,
                hotelRepository = bookingApplication().repositoryContext.hotelRepository,
                locationRepository = bookingApplication().repositoryContext.locationRepository,
            )
        }
        initializer { DocumentViewModel() }
        initializer {
            HistoryViewModel(
                hotelRepository = bookingApplication().repositoryContext.hotelRepository,
                historyRepository = bookingApplication().repositoryContext.historyRepository,
            )
        }
        initializer {
            HotelViewModel(
                hotelRepository = bookingApplication().repositoryContext.hotelRepository,
            )
        }
        initializer { ProfileViewModel() }
    }
}
