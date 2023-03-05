package com.etu.booking.mapper

import com.etu.booking.data.entity.CredentialEntity
import com.etu.booking.model.CredentialModel
import java.util.*

fun CredentialEntity.toModel() =
    CredentialModel(
        id = UUID.fromString(id),
        login = login,
        password = password,
    )
