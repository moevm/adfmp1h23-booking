package com.etu.booking.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.etu.booking.data.constant.LOCATION_TABLE

@Entity(tableName = LOCATION_TABLE)
data class LocationEntity(
    @PrimaryKey
    val id: String,

    val city: String,

    val country: String,
)