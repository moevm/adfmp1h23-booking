package com.etu.booking.data.repository

import com.etu.booking.data.entity.FacilityEntity
import com.etu.booking.data.entity.HotelEntity
import kotlinx.coroutines.flow.Flow


interface HotelRepository : CrudRepository<HotelEntity, String> {

    fun findAllByFilters(
        country: String,
        city: String,
        start: String,
        end: String,
        minPrice: Int,
        maxPrice: Int,
        maxDistance: Int,
        guestCount: Int,
    ): Flow<List<HotelEntity>>

    fun findExtendedById(id: String): Flow<Map<HotelEntity, List<FacilityEntity>>>
}
