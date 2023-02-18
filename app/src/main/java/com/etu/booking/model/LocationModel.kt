package com.etu.booking.model

data class LocationModel(
    val city: String,
    val country: String,
) {

    companion object {
        fun create(location: String): LocationModel {
            val values = location.split(" ")
            return if (values.size > 1) {
                LocationModel(values[1], values[0])
            } else {
                LocationModel("", values[0])
            }
        }
    }
}