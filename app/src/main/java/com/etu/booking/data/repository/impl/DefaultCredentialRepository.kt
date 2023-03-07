package com.etu.booking.data.repository.impl

import android.util.Log
import com.etu.booking.constant.BOOKING_LOG_TAG
import com.etu.booking.data.dao.CredentialDao
import com.etu.booking.data.entity.CredentialEntity
import com.etu.booking.data.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty

class DefaultCredentialRepository(
    private val credentialDao: CredentialDao,
) : CredentialRepository {

    override suspend fun insert(entity: CredentialEntity) {
        Log.d(BOOKING_LOG_TAG, "Inserting $ENTITY = $entity")
        credentialDao.insert(entity)
    }

    override suspend fun update(entity: CredentialEntity) {
        Log.d(BOOKING_LOG_TAG, "Updating $ENTITY = $entity")
        credentialDao.update(entity)
    }

    override suspend fun delete(entity: CredentialEntity) {
        Log.d(BOOKING_LOG_TAG, "Deleting $ENTITY = $entity")
        credentialDao.delete(entity)
    }

    override fun findById(id: String): Flow<CredentialEntity> {
        Log.d(BOOKING_LOG_TAG, "Searching $ENTITY with id = $id")
        return credentialDao.findById(id)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override fun findAll(): Flow<List<CredentialEntity>> {
        Log.d(BOOKING_LOG_TAG, "Searching all $ENTITY")
        return credentialDao.findAll()
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override fun findByLoginAndPassword(login: String, password: String): Flow<CredentialEntity> {
        Log.d(BOOKING_LOG_TAG, "Searching $ENTITY by login = $login and password = $password")
        return credentialDao.findByLoginAndPassword(login, password)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    override fun findByLogin(login: String): Flow<CredentialEntity> {
        Log.d(BOOKING_LOG_TAG, "Searching $ENTITY by login = $login")
        return credentialDao.findByLogin(login)
            .onEach { Log.d(BOOKING_LOG_TAG, "Found $ENTITY = $it") }
            .onEmpty { Log.d(BOOKING_LOG_TAG, NO_ENTITY_FOUND) }
    }

    companion object {

        private const val ENTITY = "credentials"
        private const val NO_ENTITY_FOUND = "No $ENTITY found"
    }
}