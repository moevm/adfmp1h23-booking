package com.etu.booking.mapper

import com.etu.booking.data.entity.HistoryEntity
import com.etu.booking.model.BookingHotelModel
import java.time.format.DateTimeFormatter
import java.util.*

fun BookingHotelModel.toHistoryEntity() =
    HistoryEntity(
        id = UUID.randomUUID().toString(),
        hotelId = hotelId.toString(),
        personId = personId.toString(),
        checkIn = dateFormat.format(checkIn),
        checkOut = dateFormat.format(checkOut),
        status = status.name,
        fullPrice = fullPrice,
        currency = currency,
    )

private var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")