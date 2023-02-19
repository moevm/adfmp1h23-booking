package com.etu.booking.model

import androidx.annotation.DrawableRes

data class HotelCardModel(
    @DrawableRes val imageResource: Int,
    val name: String,
    val score: Int,
    val kmFromCenter: Int,
    val pricePerNight: Int,
    val currency: String,
)
