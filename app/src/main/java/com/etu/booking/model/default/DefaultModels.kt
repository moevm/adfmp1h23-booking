package com.etu.booking.model.default

import com.etu.booking.R
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.BookingStatus.BOOKED
import com.etu.booking.model.BookingStatus.STAYED
import com.etu.booking.model.HistoryHotelModel
import com.etu.booking.model.HotelCardModel
import com.etu.booking.model.HotelModel
import com.etu.booking.model.LocationModel
import com.etu.booking.model.PersonModel
import java.time.LocalDate
import java.util.*

object DefaultModels {

    private const val EURO_CURRENCY = "EUR"
    private const val CITY = "Grey Eagle"
    private const val COUNTRY = "United States"
    private val HOTEL_NAMES = listOf(
        "Ambio Hotel",
        "Mellow Valley Hotel",
        "Parallel Vista Hotel",
        "Prince's Shield Hotel",
    )

    val BOOKING_SEARCH_MODEL = BookingSearchModel(
        location = LocationModel(city = CITY, country = COUNTRY),
        checkIn = LocalDate.of(2022, 7, 10),
        checkOut = LocalDate.of(2022, 7, 20),
        guestsAmount = 3
    )

    val IMAGES = listOf(
        R.drawable.default_hotel_1,
        R.drawable.default_hotel_2,
        R.drawable.default_hotel_3,
        R.drawable.default_hotel_4,
    )
    val PASSPORT_IMAGES = listOf(
        R.drawable.passport,
        R.drawable.passport,
        R.drawable.passport,
        R.drawable.passport,
    )

    val HOTEL_CARDS_MODELS = listOf(
        HotelCardModel(
            id = UUID.fromString("d7abe994-762a-471a-9dfe-494f2801d69d"),
            imageResource = IMAGES[0],
            name = HOTEL_NAMES[0],
            score = 8,
            kmFromCenter = 2,
            pricePerNight = 87,
            currency = EURO_CURRENCY,
        ),
        HotelCardModel(
            id = UUID.fromString("f91358a4-5f9c-4169-ac20-a9630f218dd4"),
            imageResource = IMAGES[1],
            name = HOTEL_NAMES[1],
            score = 9,
            kmFromCenter = 1,
            pricePerNight = 120,
            currency = EURO_CURRENCY,
        ),
        HotelCardModel(
            id = UUID.fromString("b9a8c372-0b0a-44ba-a606-0dad1ffd3c4b"),
            imageResource = IMAGES[2],
            name = HOTEL_NAMES[2],
            score = 7,
            kmFromCenter = 3,
            pricePerNight = 64,
            currency = EURO_CURRENCY,
        ),
        HotelCardModel(
            id = UUID.fromString("cbd1e333-8a43-4f88-a6fe-faab2a8bc848"),
            imageResource = IMAGES[3],
            name = HOTEL_NAMES[3],
            score = 8,
            kmFromCenter = 1,
            pricePerNight = 104,
            currency = EURO_CURRENCY,
        ),
    )

    val HOTEL_MODELS = listOf(
        HotelModel(
            id = UUID.fromString("d7abe994-762a-471a-9dfe-494f2801d69d"),
            imageResource = IMAGES[0],
            name = HOTEL_NAMES[0],
            address = "Belarus, Vitebsk, Chapaeva st. 34",
            score = 8,
            pricePerNight = 87,
            currency = EURO_CURRENCY,
            availableSeatsCount = 12,
            facilities = listOf("Wi-Fi", "Bath"),
            description = "Beautiful hotel with great view"
        ),
        HotelModel(
            id = UUID.fromString("f91358a4-5f9c-4169-ac20-a9630f218dd4"),
            imageResource = IMAGES[1],
            name = HOTEL_NAMES[1],
            address = "Belarus, Vitebsk, Chapaeva st. 34",
            score = 9,
            pricePerNight = 120,
            currency = EURO_CURRENCY,
            availableSeatsCount = 2,
            facilities = listOf("Wi-Fi", "Breakfast"),
            description = "Beautiful hotel in historical place"
        ),
        HotelModel(
            id = UUID.fromString("b9a8c372-0b0a-44ba-a606-0dad1ffd3c4b"),
            imageResource = IMAGES[2],
            name = HOTEL_NAMES[2],
            address = "Belarus, Vitebsk, Chapaeva st. 34",
            score = 7,
            pricePerNight = 64,
            currency = EURO_CURRENCY,
            availableSeatsCount = 2,
            facilities = listOf("Wi-Fi", "Breakfast"),
            description = "Beautiful hotel in historical place"
        ),
        HotelModel(
            id = UUID.fromString("cbd1e333-8a43-4f88-a6fe-faab2a8bc848"),
            imageResource = IMAGES[3],
            name = HOTEL_NAMES[3],
            address = "Belarus, Vitebsk, Chapaeva st. 34",
            score = 8,
            pricePerNight = 104,
            currency = EURO_CURRENCY,
            availableSeatsCount = 2,
            facilities = listOf("Wi-Fi", "Breakfast"),
            description = "Beautiful hotel in historical place"
        ),
    )

    val CITIES = listOf(
        LocationModel(CITY, COUNTRY),
        LocationModel("Vitebsk", "Belarus"),
        LocationModel("Minsk", "Belarus"),
    )

    val HISTORY_HOTELS_MODELS = listOf(
        HistoryHotelModel(
            name = HOTEL_NAMES[0],
            checkIn = LocalDate.of(2023, 1, 1),
            checkOut = LocalDate.of(2023, 1, 12),
            address = "Belarus, Vitebsk, Chapaeva st. 34",
            fullPrice = 540,
            currency = EURO_CURRENCY,
            status = BOOKED,
        ),
        HistoryHotelModel(
            name = HOTEL_NAMES[1],
            checkIn = LocalDate.of(2022, 10, 25),
            checkOut = LocalDate.of(2022, 11, 7),
            address = "Belarus, Vitebsk, Chapaeva st. 34",
            fullPrice = 632,
            currency = EURO_CURRENCY,
            status = BOOKED,
        ),
        HistoryHotelModel(
            name = HOTEL_NAMES[2],
            checkIn = LocalDate.of(2021, 5, 3),
            checkOut = LocalDate.of(2022, 1, 13),
            address = "Belarus, Vitebsk, Chapaeva st. 34",
            fullPrice = 436,
            currency = EURO_CURRENCY,
            status = STAYED,
        ),
        HistoryHotelModel(
            name = HOTEL_NAMES[3],
            checkIn = LocalDate.of(2021, 5, 3),
            checkOut = LocalDate.of(2021, 5, 14),
            address = "Belarus, Vitebsk, Chapaeva st. 34",
            fullPrice = 120,
            currency = EURO_CURRENCY,
            status = STAYED,
        ),
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