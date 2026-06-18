package com.example.a11dejunho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a11dejunho.data.repository.ProductRepository
import com.example.a11dejunho.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
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
                        },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Erro ao carregar produtos: ${e.message}"
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.isNotEmpty()) {
            searchProducts(query)
        } else {
            loadProducts()
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            try {
                productRepository.searchProducts(query).collect { products ->
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
                    errorMessage = "Erro ao buscar produtos: ${e.message}"
                )
            }
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.ProductEntity(
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    stockQuantity = product.stockQuantity
                )
                productRepository.insertProduct(entity)
                loadProducts()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao inserir produto: ${e.message}"
                )
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.ProductEntity(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    stockQuantity = product.stockQuantity
                )
                productRepository.updateProduct(entity)
                loadProducts()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao atualizar produto: ${e.message}"
                )
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.ProductEntity(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    stockQuantity = product.stockQuantity
                )
                productRepository.deleteProduct(entity)
                loadProducts()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao deletar produto: ${e.message}"
                )
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

