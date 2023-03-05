package com.etu.booking.dependecyinjection.holder

import com.etu.booking.viewmodel.AuthViewModel
import com.etu.booking.viewmodel.BookingSearchViewModel
import com.etu.booking.viewmodel.CredentialViewModel
import com.etu.booking.viewmodel.DocumentViewModel
import com.etu.booking.viewmodel.HistoryViewModel
import com.etu.booking.viewmodel.HotelViewModel
import com.etu.booking.viewmodel.ProfileViewModel

data class ViewModelHolder(
    val credentialViewModel: CredentialViewModel,
    val authViewModel: AuthViewModel,
    val bookingSearchViewModel: BookingSearchViewModel,
    val documentViewModel: DocumentViewModel,
    val historyViewModel: HistoryViewModel,
    val hotelViewModel: HotelViewModel,
    val profileViewModel: ProfileViewModel,
)
