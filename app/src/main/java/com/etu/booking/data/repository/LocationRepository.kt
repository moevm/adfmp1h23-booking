package com.etu.booking.data.repository

import com.etu.booking.data.entity.LocationEntity
import kotlinx.coroutines.flow.Flow


interface LocationRepository : CrudRepository<LocationEntity, String> {

    fun findAllByCityAndCountryRegex(
        country: String,
        city: String,
    ): Flow<List<LocationEntity>>
}
