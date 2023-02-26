package com.etu.booking.model

import java.time.LocalDate
import javax.validation.constraints.FutureOrPresent
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class BookingSearchModel(

    val location: LocationModel? = null,

    @field:FutureOrPresent
    val checkIn: LocalDate? = null,

    @field:FutureOrPresent
    val checkOut: LocalDate? = null,

    val minPricePerNight: Int? = null,

    val maxPricePerNight: Int? = null,

    val maxDistanceToCenterInKm: Int? = null,

    @field:Min(1)
    @field:Max(10)
    val guestsAmount: Int? = null,

    val errorModel: BookingSearchErrorModel = BookingSearchErrorModel(),

    private val locationPattern: Regex = Regex("([A-Za-z- ]{0,30}), ([A-Za-z- ]{0,30})"),

)
