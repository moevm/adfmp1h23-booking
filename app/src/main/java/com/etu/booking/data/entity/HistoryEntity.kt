package com.etu.booking.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.etu.booking.data.constant.HISTORY_TABLE
import java.util.*

@Entity(tableName = HISTORY_TABLE)
data class HistoryEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo("hotel_id")
    val hotelId: String,

    @ColumnInfo("person_id")
    val personId: String,

    @ColumnInfo("check_in")
    val checkIn: String,

    @ColumnInfo("check_out")
    val checkOut: String,

    val status: String,

    @ColumnInfo("full_price")
    val fullPrice: Int,

    val currency: String,
)