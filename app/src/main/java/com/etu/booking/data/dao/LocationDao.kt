package com.etu.booking.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.etu.booking.data.constant.LOCATION_TABLE
import com.etu.booking.data.entity.LocationEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface LocationDao : CrudDao<LocationEntity> {

    @Query(
        """
            SELECT *
            FROM $LOCATION_TABLE
            WHERE id = :id
        """
    )
    fun findById(id: String): Flow<LocationEntity>

    @Query(
        """
            SELECT *
            FROM $LOCATION_TABLE
        """
    )
    fun findAll(): Flow<List<LocationEntity>>

    @Query(
        """
            SELECT *
            FROM $LOCATION_TABLE
            WHERE instr(lower(country), lower(:country)) AND
                  instr(lower(city), lower(:city))
        """
    )
    fun findAllByFilters(
        country: String,
        city: String,
    ): Flow<List<LocationEntity>>
}
