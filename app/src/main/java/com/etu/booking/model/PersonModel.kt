package com.etu.booking.model

import androidx.annotation.DrawableRes
import java.time.LocalDate
import javax.validation.constraints.Past

data class PersonModel(

    var login: String,

    var password: String,

    var name: String,

    var surname: String,

    @field:Past
    var birthdate: LocalDate,

    @DrawableRes
    val avatarResource: Int? = null

)