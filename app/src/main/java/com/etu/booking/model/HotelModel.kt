package com.etu.booking.model

import androidx.annotation.DrawableRes

data class HotelModel(
    @DrawableRes val imageResource: Int,
    val name: String,
    val score: Int,
    val pricePerNight: Int,
    val currency: String,
)
