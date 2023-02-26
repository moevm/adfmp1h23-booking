package com.etu.booking.model.filter

import com.etu.booking.enumerator.BookingStatus

data class HistoryFilter(
    val checkIn: SortingType = SortingType.DEFAULT,
    val checkOut: SortingType = SortingType.DEFAULT,
    val bookingStatus: BookingStatus? = null,
)
