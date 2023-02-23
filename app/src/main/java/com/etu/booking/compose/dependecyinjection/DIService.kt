package com.etu.booking.compose.dependecyinjection

import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.view.AuthViewModel
import com.etu.booking.view.AuthorizationViewModel
import com.etu.booking.view.BookingSearchViewModel

object DIService {

    val snackbarManager = SnackbarManager()
    val authorizationViewModel = AuthorizationViewModel(snackbarManager)
    val authViewModel = AuthViewModel(snackbarManager)
    val bookingSearchViewModel = BookingSearchViewModel(snackbarManager)
}