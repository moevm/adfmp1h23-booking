package com.etu.booking.data.repository

import com.etu.booking.data.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow


interface HistoryRepository : CrudRepository<HistoryEntity, String> {

    fun existByHotelIdAndPersonIdAndDates(
        hotelId: String,
        personId: String,
        checkIn: String,
        checkOut: String
    ): Boolean

    fun findAllByPersonId(personId: String): Flow<List<HistoryEntity>>
}
