package com.etu.booking.dependecyinjection.context.impl

import android.content.Context
import com.etu.booking.data.database.BookingDatabase
import com.etu.booking.data.repository.HistoryRepository
import com.etu.booking.data.repository.HotelRepository
import com.etu.booking.data.repository.LocationRepository
import com.etu.booking.data.repository.impl.DefaultHistoryRepository
import com.etu.booking.data.repository.impl.DefaultHotelRepository
import com.etu.booking.data.repository.impl.DefaultLocationRepository
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

    override val locationRepository: LocationRepository by lazy {
        DefaultLocationRepository(BookingDatabase.getDatabase(context).locationDao())
    }
}
