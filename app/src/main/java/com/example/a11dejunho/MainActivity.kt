package com.example.a11dejunho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.a11dejunho.data.repository.SettingsRepository
import com.example.a11dejunho.ui.navigation.AppNavigation
import com.example.a11dejunho.ui.theme._11DeJunhoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val settingsRepository = SettingsRepository(this)
        
        setContent {
            // Observar o tema escuro do DataStore
            val isDarkTheme by settingsRepository.darkThemeFlow.collectAsState(initial = false)
            
            _11DeJunhoTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController, this@MainActivity)
                }
            }
        }
    }
}
