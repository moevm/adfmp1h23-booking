package com.etu.booking.enumerator

enum class BookingStatus {
    BOOKED,
    CANCELLED,
    STAYED;

    companion object {
        fun findValue(status: String) = values().firstOrNull { it.name == status }
    }
}