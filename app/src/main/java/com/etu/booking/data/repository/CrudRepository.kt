package com.etu.booking.data.repository

import kotlinx.coroutines.flow.Flow

interface CrudRepository<E, ID> {

    suspend fun insert(entity: E)
    suspend fun update(entity: E)
    suspend fun delete(entity: E)
    fun findById(id: ID): Flow<E>
    fun findAll(): Flow<List<E>>
}