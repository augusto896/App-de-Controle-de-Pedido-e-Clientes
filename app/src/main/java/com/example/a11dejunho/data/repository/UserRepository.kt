package com.example.a11dejunho.data.repository

import com.example.a11dejunho.data.dao.UserDao
import com.example.a11dejunho.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    
    suspend fun authenticate(username: String, password: String): UserEntity? {
        return userDao.authenticate(username, password)
    }

    suspend fun findByUsername(username: String): UserEntity? {
        return userDao.findByUsername(username)
    }

    suspend fun registerUser(username: String, password: String): Boolean {
        return try {
            val existingUser = userDao.findByUsername(username)
            if (existingUser == null) {
                userDao.insert(UserEntity(username = username, password = password))
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }
}

