package com.etu.booking.dependecyinjection.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.etu.booking.BookingApplication

fun CreationExtras.bookingApplication(): BookingApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookingApplication)