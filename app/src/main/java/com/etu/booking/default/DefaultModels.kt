package com.etu.booking.default

import com.etu.booking.R
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.HistoryHotelModel
import com.etu.booking.model.HotelModel
import com.etu.booking.model.LocationModel
import java.time.LocalDate

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

    val HOTELS_MODELS = listOf(
        HotelModel(
            imageResource = R.drawable.default_hotel_1,
            name = HOTEL_NAMES[0],
            score = 8,
            pricePerNight = 87,
            currency = EURO_CURRENCY,
        ),
        HotelModel(
            imageResource = R.drawable.default_hotel_2,
            name = HOTEL_NAMES[1],
            score = 9,
            pricePerNight = 120,
            currency = EURO_CURRENCY,
        ),
        HotelModel(
            imageResource = R.drawable.default_hotel_3,
            name = HOTEL_NAMES[2],
            score = 7,
            pricePerNight = 64,
            currency = EURO_CURRENCY,
        ),
        HotelModel(
            imageResource = R.drawable.default_hotel_4,
            name = HOTEL_NAMES[3],
            score = 8,
            pricePerNight = 104,
            currency = EURO_CURRENCY,
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
            fullPrice = 540,
            currency = EURO_CURRENCY,
        ),
        HistoryHotelModel(
            name = HOTEL_NAMES[1],
            checkIn = LocalDate.of(2022, 10, 25),
            checkOut = LocalDate.of(2022, 11, 7),
            fullPrice = 632,
            currency = EURO_CURRENCY,
        ),
        HistoryHotelModel(
            name = HOTEL_NAMES[2],
            checkIn = LocalDate.of(2021, 12, 25),
            checkOut = LocalDate.of(2022, 1, 13),
            fullPrice = 436,
            currency = EURO_CURRENCY,
        ),
        HistoryHotelModel(
            name = HOTEL_NAMES[3],
            checkIn = LocalDate.of(2021, 5, 3),
            checkOut = LocalDate.of(2021, 5, 14),
            fullPrice = 120,
            currency = EURO_CURRENCY,
        )
    )
}
