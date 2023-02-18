package com.example.booking.model

import java.time.LocalDate
import javax.validation.constraints.FutureOrPresent
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class BookingSearchModel(

    var location: CityModel? = null,

    @field:FutureOrPresent
    var checkIn: LocalDate? = null,

    @field:FutureOrPresent
    var checkOut: LocalDate? = null,

    @field:Min(1)
    @field:Max(10)
    var guestNumber: Int? = null,
)