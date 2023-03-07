package com.etu.booking.data.repository.impl

import com.etu.booking.data.dao.HistoryDao
import com.etu.booking.data.entity.HistoryEntity
import com.etu.booking.data.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class DefaultHistoryRepository(
    private val historyDao: HistoryDao,
) : HistoryRepository {
    override fun existByHotelIdAndPersonIdAndDates(
        hotelId: String,
        personId: String,
        checkIn: String,
        checkOut: String
    ) = historyDao.existByHotelIdAndPersonIdAndDates(
        hotelId = hotelId,
        personId = personId,
        checkIn = checkIn,
        checkOut = checkOut
    )

    override fun findAllByPersonId(personId: String): Flow<List<HistoryEntity>> =
        historyDao.findAllByPersonId(personId)

    override suspend fun insert(entity: HistoryEntity) = historyDao.insert(entity)

    override suspend fun update(entity: HistoryEntity) = historyDao.update(entity)

    override suspend fun delete(entity: HistoryEntity) = historyDao.delete(entity)

    override fun findById(id: String): Flow<HistoryEntity> = historyDao.findById(id)

    override fun findAll(): Flow<List<HistoryEntity>> = historyDao.findAll()
}
