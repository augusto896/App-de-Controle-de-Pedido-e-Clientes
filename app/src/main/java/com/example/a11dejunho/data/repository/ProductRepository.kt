package com.example.a11dejunho.data.repository

import com.example.a11dejunho.data.dao.ProductDao
import com.example.a11dejunho.data.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    
    suspend fun insertProduct(product: ProductEntity): Long {
        return productDao.insert(product)
    }

    suspend fun updateProduct(product: ProductEntity) {
        productDao.update(product)
    }

    suspend fun deleteProduct(product: ProductEntity) {
        productDao.delete(product)
    }

    suspend fun getProductById(id: Int): ProductEntity? {
        return productDao.findById(id)
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun searchProducts(query: String): Flow<List<ProductEntity>> {
        return productDao.searchProducts(query)
    }
}

