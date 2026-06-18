package com.example.a11dejunho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a11dejunho.data.repository.ClientRepository
import com.example.a11dejunho.domain.model.Client
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class ClientUiState(
    val clients: List<Client> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)

class ClientViewModel(private val clientRepository: ClientRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ClientUiState())
    val uiState: StateFlow<ClientUiState> = _uiState

    init {
        loadClients()
    }

    private fun loadClients() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
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
                        },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Erro ao carregar clientes: ${e.message}"
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.isNotEmpty()) {
            searchClients(query)
        } else {
            loadClients()
        }
    }

    private fun searchClients(query: String) {
        viewModelScope.launch {
            try {
                clientRepository.searchClients(query).collect { clients ->
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
                    errorMessage = "Erro ao buscar clientes: ${e.message}"
                )
            }
        }
    }

    fun insertClient(client: Client) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.ClientEntity(
                    name = client.name,
                    phone = client.phone,
                    email = client.email,
                    city = client.city
                )
                clientRepository.insertClient(entity)
                loadClients()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao inserir cliente: ${e.message}"
                )
            }
        }
    }

    fun updateClient(client: Client) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.ClientEntity(
                    id = client.id,
                    name = client.name,
                    phone = client.phone,
                    email = client.email,
                    city = client.city
                )
                clientRepository.updateClient(entity)
                loadClients()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao atualizar cliente: ${e.message}"
                )
            }
        }
    }

    fun deleteClient(client: Client) {
        viewModelScope.launch {
            try {
                val entity = com.example.a11dejunho.data.entity.ClientEntity(
                    id = client.id,
                    name = client.name,
                    phone = client.phone,
                    email = client.email,
                    city = client.city
                )
                clientRepository.deleteClient(entity)
                loadClients()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao deletar cliente: ${e.message}"
                )
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

