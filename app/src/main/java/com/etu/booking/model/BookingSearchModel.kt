package com.etu.booking.model

import java.time.LocalDate

data class BookingSearchModel(

    val location: LocationModel? = null,

    val checkIn: LocalDate? = null,

    val checkOut: LocalDate? = null,

    val minPricePerNight: Int? = null,

    val maxPricePerNight: Int? = null,

    val maxDistanceToCenterInKm: Int? = null,

    val guestsAmount: Int? = null,

    val errorModel: BookingSearchErrorModel = BookingSearchErrorModel(),

    private val locationPattern: Regex = Regex("([A-Za-z- ]{0,30}), ([A-Za-z- ]{0,30})"),

)
