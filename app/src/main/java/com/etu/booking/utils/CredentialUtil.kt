package com.etu.booking.utils

import com.etu.booking.model.CredentialModel

fun CredentialModel?.authorized(): Boolean {
    return this != null
}