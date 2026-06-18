package com.example.a11dejunho.domain.model

data class Order(
    val id: Int = 0,
    val clientId: Int = 0,
    val productId: Int = 0,
    val quantity: Int = 0,
    val orderDate: String = "",
    val orderTime: String = "",
    val totalValue: Double = 0.0
)

