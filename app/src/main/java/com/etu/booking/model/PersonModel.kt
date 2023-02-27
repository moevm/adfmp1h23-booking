package com.etu.booking.model

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class PersonModel(

    val login: String,

    val password: String,

    val name: String,

    val surname: String,

    val birthdate: LocalDate,

    @DrawableRes
    val avatarResource: Int? = null,

    val passport: PassportModel,
) {
    data class PassportModel(

        val nationality: String,

        val number: String,

        val expiresAt: LocalDate,
    )
}