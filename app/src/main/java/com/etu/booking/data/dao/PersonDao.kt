package com.etu.booking.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.etu.booking.constant.PERSON_TABLE
import com.etu.booking.data.entity.PersonEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface PersonDao : CrudDao<PersonEntity> {

    @Query(
        """
            SELECT *
            FROM $PERSON_TABLE
            WHERE id = :id
        """
    )
    fun findById(id: String): Flow<PersonEntity>

    @Query(
        """
            SELECT *
            FROM $PERSON_TABLE
        """
    )
    fun findAll(): Flow<List<PersonEntity>>
}
