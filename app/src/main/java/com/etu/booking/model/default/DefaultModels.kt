package com.etu.booking.model.default

import com.etu.booking.R
import com.etu.booking.model.PersonModel
import java.time.LocalDate

object DefaultModels {

    val IMAGES = mapOf(
        1 to R.drawable.default_hotel_1,
        2 to R.drawable.default_hotel_2,
        3 to R.drawable.default_hotel_3,
        4 to R.drawable.default_hotel_4,
        5 to R.drawable.default_hotel_5,
        6 to R.drawable.default_hotel_6,
        7 to R.drawable.default_hotel_7,
        8 to R.drawable.default_hotel_8,
        9 to R.drawable.default_hotel_9,
        10 to R.drawable.default_hotel_10,
        11 to R.drawable.default_hotel_11,
        12 to R.drawable.default_hotel_12,
        13 to R.drawable.default_hotel_13,
        14 to R.drawable.default_hotel_14,
        15 to R.drawable.default_hotel_15,
    )

    const val DEFAULT_AVATAR: Int = R.drawable.default_avatar

    val PERSON_MODEL = PersonModel(
        login = "kate",
        password = "1234",
        name = "Ekaterina",
        surname = "Aksyonova",
        birthdate = LocalDate.of(2001, 11, 14),
        avatarResource = R.drawable.default_avatar_1,
        passport = PersonModel.PassportModel(
            nationality = "Belarus",
            number = "PP5672456",
            expiresAt = LocalDate.now(),
        )
    )
}
