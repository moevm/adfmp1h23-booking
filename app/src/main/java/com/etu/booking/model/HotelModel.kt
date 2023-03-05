package com.etu.booking.model

import androidx.annotation.DrawableRes
import java.util.*

data class HotelModel(

    val id: UUID,

    @DrawableRes
    val imageResource: Int,

    val name: String,

    val address: String,

    val score: Int,

    val pricePerNight: Int,

    val currency: String,

    val availableSeatsCount: Int,

    val kmFromCenter: Int,

    val facilities: List<String>,

    val description: String,
)