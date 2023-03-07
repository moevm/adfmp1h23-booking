package com.etu.booking.mapper

import com.etu.booking.data.entity.HistoryEntity
import com.etu.booking.model.BookingStatus
import com.etu.booking.model.BookingStatus.Companion.getValue
import com.etu.booking.model.HistoryHotelModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun HistoryEntity.toModel(name: String, address: String) =
    HistoryHotelModel(
        checkIn = LocalDate.parse(checkIn, dateFormat),
        checkOut = LocalDate.parse(checkOut, dateFormat),
        fullPrice = fullPrice,
        status = getValue(status) ?: BookingStatus.UNKNOWN,
        currency = currency,
        name = name,
        address = address,
    )


private var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")