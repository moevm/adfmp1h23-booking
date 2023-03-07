package com.etu.booking.data.repository.impl

import android.util.Log
import com.etu.booking.constant.BOOKING_LOG_TAG
import com.etu.booking.data.dao.HotelDao
import com.etu.booking.data.entity.FacilityEntity
import com.etu.booking.data.entity.HotelEntity
import com.etu.booking.data.repository.HotelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

class DefaultHotelRepository(
    private val hotelDao: HotelDao,
) : HotelRepository {
    override fun findAllByFilters(
        country: String,
        city: String,
        start: String,
        end: String,
        minPrice: Int,
        maxPrice: Int,
        maxDistance: Int,
        guestCount: Int,
    ): Flow<List<HotelEntity>> {
        Log.d(
            BOOKING_LOG_TAG,
            "Searching $ENTITY with (country = $country, city = $city, " +
                    "start = $start, end = $end, minPrice = $minPrice, maxPrice = $maxPrice, " +
                    "maxDistance = $maxDistance, guestCount = $guestCount)"
        )
        return hotelDao.findAllByFilters(
            country = country,
            city = city,
            minPrice = minPrice,
            maxPrice = maxPrice,
            maxDistance = maxDistance,
            guestCount = guestCount,
        )
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override suspend fun insert(entity: HotelEntity) {
        Log.d(BOOKING_LOG_TAG, "Inserting $ENTITY = $entity")
        hotelDao.insert(entity)
    }

    override fun findExtendedById(id: String): Flow<Map<HotelEntity, List<FacilityEntity>>> {
        Log.d(BOOKING_LOG_TAG, "Searching facilities for $ENTITY with id = $id")
        return hotelDao.findExtendedById(id = id)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY with facilities = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override suspend fun update(entity: HotelEntity) {
        Log.d(BOOKING_LOG_TAG, "Updating $ENTITY = $entity")
        hotelDao.update(entity)
    }

    override suspend fun delete(entity: HotelEntity) {
        Log.d(BOOKING_LOG_TAG, "Deleting $ENTITY = $entity")
        hotelDao.delete(entity)
    }

    override fun findById(id: String): Flow<HotelEntity> {
        Log.d(BOOKING_LOG_TAG, "Searching $ENTITY with id = $id")
        return hotelDao.findById(id)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override fun findAll(): Flow<List<HotelEntity>> {
        Log.d(BOOKING_LOG_TAG, "Searching all $ENTITY")
        return hotelDao.findAll()
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    companion object {

        private const val ENTITY = "hotel"
        private const val NO_ENTITY_FOUND = "No $ENTITY found"
    }
}
