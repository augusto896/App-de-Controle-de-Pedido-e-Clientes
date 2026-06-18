package com.example.a11dejunho.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore by preferencesDataStore(name = "app_settings")

class SettingsRepository(private val context: Context) {
    
    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
        private val SORT_BY_KEY = stringPreferencesKey("sort_by")
    }

    // Flow para observar mudanças no tema escuro
    val darkThemeFlow: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[DARK_THEME_KEY] ?: false
    }

    val userNameFlow: Flow<String> = context.settingsDataStore.data.map { preferences ->
        preferences[USER_NAME_KEY] ?: ""
    }

    val notificationsFlow: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_KEY] ?: true
    }

    val sortByFlow: Flow<String> = context.settingsDataStore.data.map { preferences ->
        preferences[SORT_BY_KEY] ?: "name"
    }

    suspend fun setDarkTheme(isDark: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDark
        }
    }

    suspend fun setUserName(name: String) {
        context.settingsDataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    suspend fun setNotifications(enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[NOTIFICATIONS_KEY] = enabled
        }
    }

    suspend fun setSortBy(sortBy: String) {
        context.settingsDataStore.edit { preferences ->
            preferences[SORT_BY_KEY] = sortBy
        }
    }
}

