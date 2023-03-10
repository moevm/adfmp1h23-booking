package com.etu.booking.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.etu.booking.constant.HOTEL_TABLE
import java.util.*

@Entity(tableName = HOTEL_TABLE)
data class HotelEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val description: String,

    val address: String,

    val score: Int,

    val image: Int,

    @ColumnInfo("price_per_night")
    val pricePerNight: Int,

    val currency: String,

    @ColumnInfo("km_from_center")
    val kmFromCenter: Int,

    @ColumnInfo("available_seats_count")
    val availableSeatsCount: Int,
)