package com.etu.booking.default

import com.etu.booking.R
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.HotelModel
import com.etu.booking.model.LocationModel
import java.time.LocalDate

object DefaultModels {

    private const val EURO_CURRENCY = "EUR"
    private const val CITY = "Grey Eagle"
    private const val COUNTRY = "United States"

    val BOOKING_SEARCH_MODEL = BookingSearchModel(
        location = LocationModel(city = CITY, country = COUNTRY),
        checkIn = LocalDate.of(2022, 7, 10),
        checkOut = LocalDate.of(2022, 7, 20),
        guestsAmount = 3
    )

    val HOTELS_MODELS = listOf(
        HotelModel(
            imageResource = R.drawable.default_hotel_1,
            name = "Ambio Hotel",
            score = 8,
            pricePerNight = 87,
            currency = EURO_CURRENCY,
        ),
        HotelModel(
            imageResource = R.drawable.default_hotel_2,
            name = "Mellow Valley Hotel",
            score = 9,
            pricePerNight = 120,
            currency = EURO_CURRENCY,
        ),
        HotelModel(
            imageResource = R.drawable.default_hotel_3,
            name = "Parallel Vista Hotel",
            score = 7,
            pricePerNight = 64,
            currency = EURO_CURRENCY,
        ),
        HotelModel(
            imageResource = R.drawable.default_hotel_4,
            name = "Prince's Shield Hotel",
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
}
