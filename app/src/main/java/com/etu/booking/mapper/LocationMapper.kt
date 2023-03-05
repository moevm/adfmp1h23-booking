package com.etu.booking.mapper

import com.etu.booking.data.entity.LocationEntity
import com.etu.booking.model.LocationModel

fun LocationEntity.toModel() =
    LocationModel(
        city = city,
        country = country,
    )