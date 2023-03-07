package com.etu.booking.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.etu.booking.constant.CREDENTIAL_LOGIN_COLUMN_NAME
import com.etu.booking.constant.CREDENTIAL_TABLE
import java.util.*

@Entity(
    tableName = CREDENTIAL_TABLE,
    indices = [Index(value = [CREDENTIAL_LOGIN_COLUMN_NAME], unique = true)]
)
data class CredentialEntity(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = CREDENTIAL_LOGIN_COLUMN_NAME)
    val login: String,

    val password: String,
)
