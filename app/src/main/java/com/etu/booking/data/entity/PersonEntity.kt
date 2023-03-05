package com.etu.booking.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.etu.booking.data.constant.PERSON_TABLE

@Entity(tableName = PERSON_TABLE)
data class PersonEntity(
    @PrimaryKey
    val id: String,

    val login: String,

    val password: String,

    val name: String,

    val surname: String,

    val birthdate: String,

    @Embedded
    val passport: PassportEntity,

    @ColumnInfo("see_document_warning")
    var seenDocumentWarning: Boolean,
)