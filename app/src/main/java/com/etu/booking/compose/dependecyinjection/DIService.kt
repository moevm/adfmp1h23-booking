package com.etu.booking.compose.dependecyinjection

import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.view.AuthViewModel
import com.etu.booking.view.AuthorizationViewModel
import com.etu.booking.view.BookingSearchViewModel
import com.etu.booking.view.DocumentViewModel

object DIService {

    val snackbarManager = SnackbarManager()
    val authorizationViewModel = AuthorizationViewModel(snackbarManager)
    val authViewModel = AuthViewModel(snackbarManager)
    val bookingSearchViewModel = BookingSearchViewModel(snackbarManager)
    val documentViewModel = DocumentViewModel(snackbarManager)
}