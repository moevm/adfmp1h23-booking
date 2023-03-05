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
}
