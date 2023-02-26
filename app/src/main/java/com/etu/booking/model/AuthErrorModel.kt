package com.etu.booking.model

data class AuthErrorModel (

    val login: Boolean = false,

    val password: Boolean = false,

    val name: Boolean = false,

    val surname: Boolean = false,

    val birthdate: Boolean = false,

    val nationality: Boolean = false,

    val passportNumber: Boolean = false,

    val passportExpiresAt: Boolean = false

)