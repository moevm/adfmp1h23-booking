package com.etu.booking.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.etu.booking.data.constant.FACILITY_TABLE
import com.etu.booking.data.constant.HOTEL_TABLE
import com.etu.booking.data.entity.FacilityEntity
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
            WHERE instr(lower(address), lower(:country)) AND
                  instr(lower(address), lower(:city)) AND
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

    @Query(
        """
            SELECT * FROM $HOTEL_TABLE
            JOIN $FACILITY_TABLE ON $HOTEL_TABLE.id = $FACILITY_TABLE.hotel_id
            WHERE $HOTEL_TABLE.id = :id
        """
    )
    fun findExtendedById(id: String): Flow<Map<HotelEntity, List<FacilityEntity>>>
}
