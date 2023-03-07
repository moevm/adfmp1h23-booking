package com.etu.booking.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.etu.booking.data.constant.FACILITY_TABLE

@Entity(tableName = FACILITY_TABLE)
data class FacilityEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo("hotel_id")
    val hotelId: String,

    val name: String,
)