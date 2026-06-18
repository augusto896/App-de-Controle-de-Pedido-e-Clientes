package com.example.a11dejunho.data.repository

import com.example.a11dejunho.data.dao.ClientDao
import com.example.a11dejunho.data.entity.ClientEntity
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDao: ClientDao) {
    
    suspend fun insertClient(client: ClientEntity): Long {
        return clientDao.insert(client)
    }

    suspend fun updateClient(client: ClientEntity) {
        clientDao.update(client)
    }

    suspend fun deleteClient(client: ClientEntity) {
        clientDao.delete(client)
    }

    suspend fun getClientById(id: Int): ClientEntity? {
        return clientDao.findById(id)
    }

    fun getAllClients(): Flow<List<ClientEntity>> {
        return clientDao.getAllClients()
    }

    fun searchClients(query: String): Flow<List<ClientEntity>> {
        return clientDao.searchClients(query)
    }
}

