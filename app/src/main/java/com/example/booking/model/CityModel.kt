package com.example.booking.model

data class CityModel(
    val name: String,
    val country: String,
) {

    companion object {
        fun create(location: String): CityModel {
            val values = location.split(" ")
            return if (values.size > 1) {
                CityModel(values[1], values[0])
            } else {
                CityModel("", values[0])
            }
        }
    }
}