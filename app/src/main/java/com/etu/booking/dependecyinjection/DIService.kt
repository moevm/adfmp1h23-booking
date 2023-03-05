package com.etu.booking.dependecyinjection

import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.viewmodel.AuthViewModel
import com.etu.booking.viewmodel.AuthorizationViewModel
import com.etu.booking.viewmodel.BookingSearchViewModel
import com.etu.booking.viewmodel.DocumentViewModel
import com.etu.booking.viewmodel.HistoryViewModel
import com.etu.booking.viewmodel.ProfileViewModel

object DIService {

    val snackbarManager = SnackbarManager()
    val authorizationViewModel = AuthorizationViewModel(snackbarManager)
    val authViewModel = AuthViewModel()
    val bookingSearchViewModel = BookingSearchViewModel()
    val historyViewModel = HistoryViewModel()
    val profileViewModel = ProfileViewModel()
    val documentViewModel = DocumentViewModel()
}
