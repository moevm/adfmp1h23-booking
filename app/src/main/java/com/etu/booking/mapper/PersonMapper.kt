package com.etu.booking.mapper

import com.etu.booking.data.entity.PassportEntity
import com.etu.booking.data.entity.PersonEntity
import com.etu.booking.model.PersonModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun PersonEntity.toModel() =
    PersonModel(
        id = UUID.fromString(id),
        name = name,
        surname = surname,
        birthdate = LocalDate.parse(birthdate, dateFormat),
        passport = PersonModel.PassportModel(
            nationality = passport.nationality,
            number = passport.number,
            expiresAt = LocalDate.parse(passport.expiresAt, dateFormat),
        ),
        seenDocumentWarning = seenDocumentWarning,
    )

fun PersonModel.toEntity() =
    PersonEntity(
        id = id.toString(),
        name = name,
        surname = surname,
        birthdate = dateFormat.format(birthdate),
        passport = PassportEntity(
            nationality = passport.nationality,
            number = passport.number,
            expiresAt = dateFormat.format(passport.expiresAt),
        ),
        seenDocumentWarning = seenDocumentWarning,
    )

private var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")