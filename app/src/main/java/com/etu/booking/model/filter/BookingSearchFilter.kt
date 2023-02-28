package com.etu.booking.model.filter

data class BookingSearchFilter(
    val price: SortingType = SortingType.DEFAULT,
    val rating: SortingType = SortingType.DEFAULT,
    val distance: SortingType = SortingType.DEFAULT,
)
