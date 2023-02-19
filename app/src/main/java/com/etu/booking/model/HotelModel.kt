package com.etu.booking.model

import androidx.annotation.DrawableRes

data class HotelModel(

    @DrawableRes
    val imageResource: Int,

    val name: String,

    val address: String,

    val score: Int,

    val pricePerNight: Int,

    val currency: String,

    val availableSeatsCount: Int,

    val facilities: List<String>,

    val description: String,
)