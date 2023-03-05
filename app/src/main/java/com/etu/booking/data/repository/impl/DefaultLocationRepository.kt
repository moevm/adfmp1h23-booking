package com.etu.booking.data.repository.impl

import android.util.Log
import com.etu.booking.constant.BOOKING_LOG_TAG
import com.etu.booking.data.dao.LocationDao
import com.etu.booking.data.entity.LocationEntity
import com.etu.booking.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

class DefaultLocationRepository(
    private val locationDao: LocationDao,
) : LocationRepository {

    override fun findAllByCityAndCountryRegex(
        country: String,
        city: String,
    ): Flow<List<LocationEntity>> {
        Log.d(BOOKING_LOG_TAG, "Searching with country = '$country' and city = '$city'")

        return locationDao.findAllByFilters(
            country = country,
            city = city,
        )
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override suspend fun insert(entity: LocationEntity) {
        Log.d(BOOKING_LOG_TAG, "Inserting $ENTITY = $entity")
        locationDao.insert(entity)
    }

    override suspend fun update(entity: LocationEntity) {
        Log.d(BOOKING_LOG_TAG, "Updating $ENTITY = $entity")
        locationDao.update(entity)
    }

    override suspend fun delete(entity: LocationEntity) {
        Log.d(BOOKING_LOG_TAG, "Deleting $ENTITY = $entity")
        locationDao.delete(entity)
    }

    override fun findById(id: String): Flow<LocationEntity> {
        Log.d(BOOKING_LOG_TAG, "Searching $ENTITY with id = $id")
        return locationDao.findById(id)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override fun findAll(): Flow<List<LocationEntity>> {
        Log.d(BOOKING_LOG_TAG, "Searching all $ENTITY")
        return locationDao.findAll()
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    companion object {

        private const val ENTITY = "location"
        private const val NO_ENTITY_FOUND = "No $ENTITY found"
    }
}
