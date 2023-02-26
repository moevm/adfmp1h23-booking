package com.etu.booking.model

data class AuthModel (

    val login: String,

    val password: String,

    val personModel: PersonModel,

    val errorModel: AuthErrorModel

)