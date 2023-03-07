package com.etu.booking.model

import java.time.LocalDate
import java.util.*

class BookingHotelModel(

    val hotelId: UUID,

    val personId: UUID,

    val checkIn: LocalDate,

    val checkOut: LocalDate,

    val fullPrice: Int,

    val currency: String,

    val status: BookingStatus,
)