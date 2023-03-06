package com.etu.booking.mapper

import com.etu.booking.data.entity.HotelEntity
import com.etu.booking.model.HotelCardModel
import com.etu.booking.model.HotelModel
import com.etu.booking.model.default.DefaultModels
import java.util.*

fun HotelEntity.toModel() =
    HotelModel(
        id = UUID.fromString(id),
        imageResource = DefaultModels.DEFAULT_AVATAR,
        name = name,
        description = description,
        address = address,
        score = score,
        pricePerNight = pricePerNight,
        currency = currency,
        availableSeatsCount = availableSeatsCount,
        facilities = emptyList() // TODO
    )

fun HotelEntity.toCardModel() =
    HotelCardModel(
        id = UUID.fromString(id),
        imageResource = DefaultModels.DEFAULT_AVATAR,
        name = name,
        score = score,
        pricePerNight = pricePerNight,
        currency = currency,
        kmFromCenter = kmFromCenter,
    )