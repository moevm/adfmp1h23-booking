package com.etu.booking.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.etu.booking.data.constant.HOTEL_TABLE
import com.etu.booking.data.entity.HotelEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface HotelDao : CrudDao<HotelEntity> {

    @Query(
        """
            SELECT *
            FROM $HOTEL_TABLE
            WHERE id = :id
        """
    )
    fun findById(id: String): Flow<HotelEntity>

    @Query(
        """
            SELECT *
            FROM $HOTEL_TABLE
        """
    )
    fun findAll(): Flow<List<HotelEntity>>

    @Query(
        """
            SELECT *
            FROM $HOTEL_TABLE
            WHERE instr(address, :country) AND
                  instr(address, :city) AND
                  price_per_night >= :minPrice AND
                  price_per_night <= :maxPrice AND
                  km_from_center <= :maxDistance AND
                  available_seats_count >= :guestCount
        """
    )
    fun findAllByFilters(
        country: String,
        city: String,
        minPrice: Int,
        maxPrice: Int,
        maxDistance: Int,
        guestCount: Int,
    ): Flow<List<HotelEntity>>
}
