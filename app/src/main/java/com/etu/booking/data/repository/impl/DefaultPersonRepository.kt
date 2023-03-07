package com.etu.booking.data.repository.impl

import android.util.Log
import com.etu.booking.constant.BOOKING_LOG_TAG
import com.etu.booking.data.dao.PersonDao
import com.etu.booking.data.entity.PersonEntity
import com.etu.booking.data.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

class DefaultPersonRepository(
    private val personDao: PersonDao,
) : PersonRepository {

    override suspend fun insert(entity: PersonEntity) {
        Log.d(BOOKING_LOG_TAG, "Inserting $ENTITY = $entity")
        personDao.insert(entity)
    }

    override suspend fun update(entity: PersonEntity) {
        Log.d(BOOKING_LOG_TAG, "Updating $ENTITY = $entity")
        personDao.update(entity)
    }

    override suspend fun delete(entity: PersonEntity) {
        Log.d(BOOKING_LOG_TAG, "Deleting ENTITY = $entity")
        personDao.delete(entity)
    }

    override fun findById(id: String): Flow<PersonEntity> {
        Log.d(BOOKING_LOG_TAG, "Searching $ENTITY by id = $id")
        return personDao.findById(id)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override fun findAll(): Flow<List<PersonEntity>> {
        Log.d(BOOKING_LOG_TAG, "Searching all $ENTITY")
        return personDao.findAll()
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    companion object {

        private const val ENTITY = "person"
        private const val NO_ENTITY_FOUND = "No $ENTITY found"
    }
}
