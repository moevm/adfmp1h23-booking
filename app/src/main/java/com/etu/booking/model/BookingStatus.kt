package com.etu.booking.model

enum class BookingStatus {
    BOOKED,
    CANCELLED,
    STAYED,
    UNKNOWN;

    companion object {
        fun getValue(value: String) =
            BookingStatus.values()
                .associateBy { it.name }
                .getOrDefault(value.uppercase(), null)
    }
}
