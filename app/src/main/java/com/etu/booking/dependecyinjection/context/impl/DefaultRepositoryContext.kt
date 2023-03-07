package com.etu.booking.dependecyinjection.context.impl

import android.content.Context
import com.etu.booking.data.database.BookingDatabase
import com.etu.booking.data.repository.HistoryRepository
import com.etu.booking.data.repository.CredentialRepository
import com.etu.booking.data.repository.HotelRepository
import com.etu.booking.data.repository.LocationRepository
import com.etu.booking.data.repository.impl.DefaultHistoryRepository
import com.etu.booking.data.repository.PersonRepository
import com.etu.booking.data.repository.impl.DefaultCredentialRepository
import com.etu.booking.data.repository.impl.DefaultHotelRepository
import com.etu.booking.data.repository.impl.DefaultLocationRepository
import com.etu.booking.data.repository.impl.DefaultPersonRepository
import com.etu.booking.dependecyinjection.context.RepositoryContext

class DefaultRepositoryContext(
    private val context: Context,
) : RepositoryContext {

    override val historyRepository: HistoryRepository by lazy {
        DefaultHistoryRepository(BookingDatabase.getDatabase(context).historyDao())
    }

    override val hotelRepository: HotelRepository by lazy {
        DefaultHotelRepository(BookingDatabase.getDatabase(context).hotelDao())
    }

    override val personRepository: PersonRepository by lazy {
        DefaultPersonRepository(BookingDatabase.getDatabase(context).personDao())
    }

    override val locationRepository: LocationRepository by lazy {
        DefaultLocationRepository(BookingDatabase.getDatabase(context).locationDao())
    }

    override val credentialRepository: CredentialRepository by lazy {
        DefaultCredentialRepository(BookingDatabase.getDatabase(context).credentialDao())
    }
}
