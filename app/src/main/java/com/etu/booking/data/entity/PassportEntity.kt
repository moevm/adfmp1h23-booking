package com.etu.booking.data.entity

import androidx.room.ColumnInfo

data class PassportEntity(

    val nationality: String,

    val number: String,

    @ColumnInfo("expires_at")
    val expiresAt: String,
)