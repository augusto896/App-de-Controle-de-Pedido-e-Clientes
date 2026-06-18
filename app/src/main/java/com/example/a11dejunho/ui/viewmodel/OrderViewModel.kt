package com.example.a11dejunho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a11dejunho.data.repository.ClientRepository
import com.example.a11dejunho.data.repository.OrderRepository
import com.example.a11dejunho.data.repository.ProductRepository
import com.example.a11dejunho.domain.model.Client
import com.example.a11dejunho.domain.model.Order
import com.example.a11dejunho.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class OrderUiState(
    val orders: List<Order> = emptyList(),
    val clients: List<Client> = emptyList(),
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val clientRepository: ClientRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState

    init {
        loadOrders()
        loadClients()
        loadProducts()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                orderRepository.getAllOrders().collect { orders ->
                    _uiState.value = _uiState.value.copy(
                        orders = orders.map { entity ->
                            Order(
                                id = entity.id,
                                clientId = entity.clientId,
                                productId = entity.productId,
                                quantity = entity.quantity,
                                orderDate = entity.orderDate,
                                orderTime = entity.orderTime,
                                totalValue = entity.totalValue
                            )
                        },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Erro ao carregar pedidos: ${e.message}"
                )
            }
        }
    }

    private fun loadClients() {
        viewModelScope.launch {
            try {
                clientRepository.getAllClients().collect { clients ->
                    _uiState.value = _uiState.value.copy(
                        clients = clients.map { entity ->
                            Client(
                                id = entity.id,
                                name = entity.name,
                                phone = entity.phone,
                                email = entity.email,
                                city = entity.city
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao carregar clientes: ${e.message}"
                )
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                productRepository.getAllProducts().collect { products ->
                    _uiState.value = _uiState.value.copy(
                        products = products.map { entity ->
                            Product(
                                id = entity.id,
                                name = entity.name,
                                description = entity.description,
                                price = entity.price,
                                stockQuantity = entity.stockQuantity
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao carregar produtos: ${e.message}"
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.isNotEmpty()) {
            searchOrders(query)
        } else {
            loadOrders()
        }
    }

    private fun searchOrders(query: String) {
        viewModelScope.launch {
            try {
                orderRepository.searchOrdersByClientName(query).collect { orders ->
                    _uiState.value = _uiState.value.copy(
                        orders = orders.map { entity ->
                            Order(
                                id = entity.id,
                                clientId = entity.clientId,
                                productId = entity.productId,
                                quantity = entity.quantity,
                                orderDate = entity.orderDate,
                                orderTime = entity.orderTime,
                                totalValue = entity.totalValue
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao buscar pedidos: ${e.message}"
                )
            }
        }
    }

    fun insertOrder(order: Order) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.OrderEntity(
                    clientId = order.clientId,
                    productId = order.productId,
                    quantity = order.quantity,
                    orderDate = order.orderDate,
                    orderTime = order.orderTime,
                    totalValue = order.totalValue
                )
                orderRepository.insertOrder(entity)
                loadOrders()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao inserir pedido: ${e.message}"
                )
            }
        }
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.OrderEntity(
                    id = order.id,
                    clientId = order.clientId,
                    productId = order.productId,
                    quantity = order.quantity,
                    orderDate = order.orderDate,
                    orderTime = order.orderTime,
                    totalValue = order.totalValue
                )
                orderRepository.updateOrder(entity)
                loadOrders()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao atualizar pedido: ${e.message}"
                )
            }
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.OrderEntity(
                    id = order.id,
                    clientId = order.clientId,
                    productId = order.productId,
                    quantity = order.quantity,
                    orderDate = order.orderDate,
                    orderTime = order.orderTime,
                    totalValue = order.totalValue
                )
                orderRepository.deleteOrder(entity)
                loadOrders()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao deletar pedido: ${e.message}"
                )
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

