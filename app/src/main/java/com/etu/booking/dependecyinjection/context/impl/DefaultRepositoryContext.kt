package com.etu.booking.dependecyinjection.context.impl

import android.content.Context
import com.etu.booking.data.database.BookingDatabase
import com.etu.booking.data.repository.HotelRepository
import com.etu.booking.data.repository.impl.DefaultHotelRepository
import com.etu.booking.dependecyinjection.context.RepositoryContext

class DefaultRepositoryContext(
    private val context: Context,
) : RepositoryContext {

    override val hotelRepository: HotelRepository by lazy {
        DefaultHotelRepository(BookingDatabase.getDatabase(context).hotelDao())
    }
}
