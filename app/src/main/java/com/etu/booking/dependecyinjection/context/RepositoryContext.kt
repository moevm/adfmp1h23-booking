package com.etu.booking.dependecyinjection.context

import com.etu.booking.data.repository.HistoryRepository
import com.etu.booking.data.repository.CredentialRepository
import com.etu.booking.data.repository.HotelRepository
import com.etu.booking.data.repository.LocationRepository
import com.etu.booking.data.repository.PersonRepository

interface RepositoryContext {
    val historyRepository: HistoryRepository
    val hotelRepository: HotelRepository
    val personRepository: PersonRepository
    val locationRepository: LocationRepository
    val credentialRepository: CredentialRepository
}
