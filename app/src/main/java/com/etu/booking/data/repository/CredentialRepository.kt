package com.etu.booking.data.repository

import com.etu.booking.data.entity.CredentialEntity
import kotlinx.coroutines.flow.Flow

interface CredentialRepository : CrudRepository<CredentialEntity, String> {

    fun findByLoginAndPassword(login: String, password: String): Flow<CredentialEntity>

    fun findByLogin(login: String): Flow<CredentialEntity>
}
