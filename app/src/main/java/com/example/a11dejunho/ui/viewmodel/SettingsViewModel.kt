package com.example.a11dejunho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a11dejunho.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class SettingsUiState(
    val isDarkTheme: Boolean = false,
    val userName: String = "",
    val notificationsEnabled: Boolean = true,
    val sortBy: String = "name",
    val isLoading: Boolean = false
)

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            // Combinar todos os flows para atualizar o estado
            combine(
                settingsRepository.darkThemeFlow,
                settingsRepository.userNameFlow,
                settingsRepository.notificationsFlow,
                settingsRepository.sortByFlow
            ) { darkTheme, userName, notifications, sortBy ->
                SettingsUiState(
                    isDarkTheme = darkTheme,
                    userName = userName,
                    notificationsEnabled = notifications,
                    sortBy = sortBy,
                    isLoading = false
                )
            }.collect { settings ->
                _uiState.value = settings
            }
        }
    }

    fun updateDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkTheme(isDark)
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            settingsRepository.setUserName(name)
        }
    }

    fun updateNotifications(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setNotifications(enabled)
        }
    }

    fun updateSortBy(sortBy: String) {
        viewModelScope.launch {
            settingsRepository.setSortBy(sortBy)
        }
    }
}

