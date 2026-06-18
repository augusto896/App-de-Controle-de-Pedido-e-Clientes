package com.example.a11dejunho.data.repository

import com.example.a11dejunho.data.dao.OrderDao
import com.example.a11dejunho.data.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {
    
    suspend fun insertOrder(order: OrderEntity): Long {
        return orderDao.insert(order)
    }

    suspend fun updateOrder(order: OrderEntity) {
        orderDao.update(order)
    }

    suspend fun deleteOrder(order: OrderEntity) {
        orderDao.delete(order)
    }

    suspend fun getOrderById(id: Int): OrderEntity? {
        return orderDao.findById(id)
    }

    fun getAllOrders(): Flow<List<OrderEntity>> {
        return orderDao.getAllOrders()
    }

    fun getOrdersByClient(clientId: Int): Flow<List<OrderEntity>> {
        return orderDao.getOrdersByClient(clientId)
    }

    fun getOrdersByProduct(productId: Int): Flow<List<OrderEntity>> {
        return orderDao.getOrdersByProduct(productId)
    }

    fun searchOrdersByClientName(query: String): Flow<List<OrderEntity>> {
        return orderDao.searchOrdersByClientName(query)
    }

    fun searchOrdersByProductName(query: String): Flow<List<OrderEntity>> {
        return orderDao.searchOrdersByProductName(query)
    }
}

