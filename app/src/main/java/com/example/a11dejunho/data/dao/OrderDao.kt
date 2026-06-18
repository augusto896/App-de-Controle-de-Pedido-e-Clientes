package com.example.a11dejunho.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.a11dejunho.data.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: OrderEntity): Long

    @Update
    suspend fun update(order: OrderEntity)

    @Delete
    suspend fun delete(order: OrderEntity)

    @Query("SELECT * FROM orders WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): OrderEntity?

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE clientId = :clientId ORDER BY orderDate DESC")
    fun getOrdersByClient(clientId: Int): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE productId = :productId ORDER BY orderDate DESC")
    fun getOrdersByProduct(productId: Int): Flow<List<OrderEntity>>

    @Query("""
        SELECT o.* FROM orders o
        WHERE o.clientId IN (
            SELECT id FROM clients WHERE name LIKE '%' || :searchQuery || '%'
        )
        ORDER BY o.orderDate DESC
    """)
    fun searchOrdersByClientName(searchQuery: String): Flow<List<OrderEntity>>

    @Query("""
        SELECT o.* FROM orders o
        WHERE o.productId IN (
            SELECT id FROM products WHERE name LIKE '%' || :searchQuery || '%'
        )
        ORDER BY o.orderDate DESC
    """)
    fun searchOrdersByProductName(searchQuery: String): Flow<List<OrderEntity>>
}

