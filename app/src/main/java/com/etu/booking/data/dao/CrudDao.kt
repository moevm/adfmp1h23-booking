package com.etu.booking.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface CrudDao<E> {

    @Insert
    suspend fun insert(entity: E)

    @Update
    suspend fun update(entity: E)

    @Delete
    suspend fun delete(entity: E)
}
