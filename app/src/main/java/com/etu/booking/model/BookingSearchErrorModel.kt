package com.etu.booking.model

data class BookingSearchErrorModel (

    val location: Boolean = false,

    val checkIn: Boolean = false,

    val checkOut: Boolean = false,

    val minPricePerNight: Boolean = false,

    val maxPricePerNight: Boolean = false,

    val maxDistanceToCenterInKm: Boolean = false,

    val guestsAmount: Boolean = false

)