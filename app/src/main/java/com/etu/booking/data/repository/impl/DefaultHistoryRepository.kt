package com.etu.booking.data.repository.impl

import android.util.Log
import com.etu.booking.constant.BOOKING_LOG_TAG
import com.etu.booking.data.dao.HistoryDao
import com.etu.booking.data.entity.HistoryEntity
import com.etu.booking.data.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

class DefaultHistoryRepository(
    private val historyDao: HistoryDao,
) : HistoryRepository {
    override fun existByHotelIdAndPersonIdAndDates(
        hotelId: String,
        personId: String,
        checkIn: String,
        checkOut: String,
    ): Boolean {
        Log.d(
            BOOKING_LOG_TAG,
            "Check $ENTITY existence by hotelId = $hotelId," +
                    " personId = $personId, checkIn = $checkIn, $checkOut"
        )
        return historyDao.existByHotelIdAndPersonIdAndDates(
            hotelId = hotelId,
            personId = personId,
            checkIn = checkIn,
            checkOut = checkOut
        ).also { Log.d(BOOKING_LOG_TAG, "$ENTITY existence: $it") }
    }

    override fun findAllByPersonId(personId: String): Flow<List<HistoryEntity>> {
        Log.d(BOOKING_LOG_TAG, "Searching $ENTITY with personId = $personId")
        return historyDao.findAllByPersonId(personId)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override suspend fun insert(entity: HistoryEntity) {
        Log.d(BOOKING_LOG_TAG, "Inserting $ENTITY = $entity")
        historyDao.insert(entity)
    }

    override suspend fun update(entity: HistoryEntity) {
        Log.d(BOOKING_LOG_TAG, "Updating $ENTITY = $entity")
        historyDao.update(entity)
    }

    override suspend fun delete(entity: HistoryEntity) {
        Log.d(BOOKING_LOG_TAG, "Deleting $ENTITY = $entity")
        historyDao.delete(entity)
    }

    override fun findById(id: String): Flow<HistoryEntity> {
        Log.d(BOOKING_LOG_TAG, "Searching $ENTITY with id = $id")
        return historyDao.findById(id)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override fun findAll(): Flow<List<HistoryEntity>> {
        Log.d(BOOKING_LOG_TAG, "Searching all $ENTITY")
        return historyDao.findAll()
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    companion object {

        private const val ENTITY = "history"
        private const val NO_ENTITY_FOUND = "No $ENTITY found"
    }
}
