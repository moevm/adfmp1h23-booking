package com.etu.booking.model

import java.util.*

data class CredentialModel(
    val id: UUID,
    val login: String,
    val password: String,
)