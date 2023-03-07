package com.etu.booking.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.etu.booking.constant.HISTORY_TABLE
import com.etu.booking.data.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface HistoryDao : CrudDao<HistoryEntity> {

    @Query(
        """
            SELECT *
            FROM $HISTORY_TABLE
            WHERE id = :id
        """
    )
    fun findById(id: String): Flow<HistoryEntity>

    @Query(
        """
            SELECT *
            FROM $HISTORY_TABLE
        """
    )
    fun findAll(): Flow<List<HistoryEntity>>

    @Query(
        """
            SELECT *
            FROM $HISTORY_TABLE
            WHERE person_id = :personId
        """
    )
    fun findAllByPersonId(personId: String): Flow<List<HistoryEntity>>

    @Query(
        """
            SELECT case when count(*) > 0 then true else false end 
            FROM $HISTORY_TABLE
            WHERE hotel_id = :hotelId AND
                  person_id = :personId AND
                  check_in = :checkIn AND
                  check_out = :checkOut

        """
    )
    fun existByHotelIdAndPersonIdAndDates(
        hotelId: String,
        personId: String,
        checkIn: String,
        checkOut: String
    ): Boolean

}
