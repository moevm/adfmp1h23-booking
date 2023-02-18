package com.example.booking.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.booking.model.BookingSearchModel
import org.jetbrains.annotations.NotNull
import java.time.format.DateTimeFormatter

class BookingSearchViewModel : ViewModel()  {

    private var bookingSearchModel = BookingSearchModel()

    var location by mutableStateOf(bookingSearchModel.location)
    var checkIn by mutableStateOf(bookingSearchModel.checkIn)
    var checkOut by mutableStateOf(bookingSearchModel.checkOut)
    var guestNumber by mutableStateOf(bookingSearchModel.guestNumber)

}