package com.etu.booking.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class HotelCardModel(

    val id: UUID,

    @DrawableRes
    val imageResource: Int,

    val name: String,

    val score: Int,

    val kmFromCenter: Int,

    val pricePerNight: Int,

    val currency: String,
)
