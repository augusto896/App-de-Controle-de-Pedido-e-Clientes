package com.example.a11dejunho.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
    
    fun formatDate(dateString: String): String {
        return try {
            dateString // Already formatted as we store it as string
        } catch (e: Exception) {
            dateString
        }
    }
    
    fun formatTime(timeString: String): String {
        return try {
            timeString // Already formatted as we store it as string
        } catch (e: Exception) {
            timeString
        }
    }
    
    fun getCurrentDateString(): String {
        val today = Date()
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today)
    }
    
    fun getCurrentTimeString(): String {
        val now = Date()
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(now)
    }
}

fun String.isValidEmail(): Boolean {
    return this.matches(
        Regex("^[A-Za-z0-9+_.-]+@(.+)$")
    )
}

fun String.isValidPhone(): Boolean {
    return this.replace(Regex("[^0-9]"), "").length >= 10
}

fun Double.formatCurrency(): String {
    return "R$ ${"%.2f".format(this)}"
}

