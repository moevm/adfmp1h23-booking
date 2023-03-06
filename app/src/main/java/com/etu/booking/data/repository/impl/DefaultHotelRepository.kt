package com.etu.booking.data.repository.impl

import com.etu.booking.data.dao.HotelDao
import com.etu.booking.data.entity.HotelEntity
import com.etu.booking.data.repository.HotelRepository
import kotlinx.coroutines.flow.Flow

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
        guestCount: Int
    )  = hotelDao.findAllByFilters(
        country = country,
        city = city,
        minPrice = minPrice,
        maxPrice = maxPrice,
        maxDistance = maxDistance,
        guestCount = guestCount,
    )

    override suspend fun insert(entity: HotelEntity) = hotelDao.insert(entity)

    override suspend fun update(entity: HotelEntity) = hotelDao.update(entity)

    override suspend fun delete(entity: HotelEntity) = hotelDao.delete(entity)

    override fun findById(id: String): Flow<HotelEntity> = hotelDao.findById(id)

    override fun findAll(): Flow<List<HotelEntity>> = hotelDao.findAll()
}
