package com.etu.booking.dependecyinjection.context

import com.etu.booking.data.repository.HotelRepository

interface RepositoryContext {
    val hotelRepository: HotelRepository
}
