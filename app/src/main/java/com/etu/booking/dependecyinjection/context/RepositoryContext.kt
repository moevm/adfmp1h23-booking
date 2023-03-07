package com.etu.booking.dependecyinjection.context

import com.etu.booking.data.repository.HistoryRepository
import com.etu.booking.data.repository.HotelRepository
import com.etu.booking.data.repository.LocationRepository

interface RepositoryContext {
    val historyRepository: HistoryRepository
    val hotelRepository: HotelRepository
    val locationRepository: LocationRepository
}
