package com.etu.booking.data.repository.impl

import com.etu.booking.data.dao.LocationDao
import com.etu.booking.data.entity.LocationEntity
import com.etu.booking.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class DefaultLocationRepository(
    private val locationDao: LocationDao,
) : LocationRepository {
    
    override fun findAllByCityAndCountryRegex(
        country: String,
        city: String,
    ) = locationDao.findAllByFilters(
        country = country,
        city = city,
    )

    override suspend fun insert(entity: LocationEntity) = locationDao.insert(entity)

    override suspend fun update(entity: LocationEntity) = locationDao.update(entity)

    override suspend fun delete(entity: LocationEntity) = locationDao.delete(entity)

    override fun findById(id: String): Flow<LocationEntity> = locationDao.findById(id)

    override fun findAll(): Flow<List<LocationEntity>> = locationDao.findAll()
}
