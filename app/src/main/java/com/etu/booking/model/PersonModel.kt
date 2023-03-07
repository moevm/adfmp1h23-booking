package com.etu.booking.model

import androidx.annotation.DrawableRes
import java.time.LocalDate
import java.util.*

data class PersonModel(

    val id: UUID? = null,

    val login: String? = null,

    val password: String? = null,

    val name: String,

    val surname: String,

    val birthdate: LocalDate,

    @DrawableRes
    val avatarResource: Int? = null,

    val passport: PassportModel,

    var seenDocumentWarning: Boolean,
) {
    data class PassportModel(

        val nationality: String,

        val number: String,

        val expiresAt: LocalDate,
    )
}