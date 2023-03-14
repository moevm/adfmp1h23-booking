package com.etu.booking.mapper

import com.etu.booking.data.entity.FacilityEntity
import com.etu.booking.data.entity.HotelEntity
import com.etu.booking.model.HotelCardModel
import com.etu.booking.model.HotelModel
import com.etu.booking.model.default.DefaultModels.DEFAULT_AVATAR
import com.etu.booking.model.default.DefaultModels.IMAGES
import java.util.*

fun HotelEntity.toModel(facilities: List<FacilityEntity>) =
    HotelModel(
        id = UUID.fromString(id),
        name = name,
        description = description,
        address = address,
        score = score,
        pricePerNight = pricePerNight,
        currency = currency,
        availableSeatsCount = availableSeatsCount,
        kmFromCenter = kmFromCenter,
        imageResource = IMAGES[image] ?: DEFAULT_AVATAR,
        facilities = facilities.map { it.name }
    )

fun HotelEntity.toCardModel() =
    HotelCardModel(
        id = UUID.fromString(id),
        imageResource = IMAGES[image] ?: DEFAULT_AVATAR,
        name = name,
        address = address,
        score = score,
        pricePerNight = pricePerNight,
        currency = currency,
        kmFromCenter = kmFromCenter,
    )