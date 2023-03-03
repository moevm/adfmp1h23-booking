package com.etu.booking.model

import java.time.LocalDate

data class HistoryHotelModel(
    val name: String,

    val checkIn: LocalDate,

    val checkOut: LocalDate,

    val address: String,

    val fullPrice: Int,

    val currency: String,

    val status: BookingStatus,
)