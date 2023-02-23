package com.etu.booking.messaging

import androidx.annotation.StringRes

data class Message(
    val id: Long,
    @StringRes val messageId: Int,
)
