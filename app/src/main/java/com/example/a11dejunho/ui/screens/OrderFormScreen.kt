package com.example.a11dejunho.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.a11dejunho.domain.model.Order
import com.example.a11dejunho.ui.viewmodel.OrderViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderFormScreen(
    viewModel: OrderViewModel,
    orderId: Int,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var selectedClientId by remember { mutableStateOf(0) }
    var selectedProductId by remember { mutableStateOf(0) }
    var quantity by remember { mutableStateOf("") }
    var orderDate by remember { mutableStateOf("") }
    var orderTime by remember { mutableStateOf("") }
    var totalValue by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    var expandedClients by remember { mutableStateOf(false) }
    var expandedProducts by remember { mutableStateOf(false) }

    LaunchedEffect(orderId) {
        if (orderId > 0) {
            isEditing = true
            val order = uiState.orders.find { it.id == orderId }
            order?.let {
                selectedClientId = it.clientId
                selectedProductId = it.productId
                quantity = it.quantity.toString()
                orderDate = it.orderDate
                orderTime = it.orderTime
                totalValue = it.totalValue.toString()
            }
        }
    }

    val title = if (isEditing) "Editar Pedido" else "Novo Pedido"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Client Selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedButton(
                    onClick = { expandedClients = true },
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    val clientName = uiState.clients.find { it.id == selectedClientId }?.name ?: "Selecione um cliente"
                    Text(clientName)
                }
                DropdownMenu(
                    expanded = expandedClients,
                    onDismissRequest = { expandedClients = false },
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    uiState.clients.forEach { client ->
                        DropdownMenuItem(
                            text = { Text(client.name) },
                            onClick = {
                                selectedClientId = client.id
                                expandedClients = false
                            }
                        )
                    }
                }
            }

            // Product Selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedButton(
                    onClick = { expandedProducts = true },
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    val productName = uiState.products.find { it.id == selectedProductId }?.name ?: "Selecione um produto"
                    Text(productName)
                }
                DropdownMenu(
                    expanded = expandedProducts,
                    onDismissRequest = { expandedProducts = false },
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    uiState.products.forEach { product ->
                        DropdownMenuItem(
                            text = { Text(product.name) },
                            onClick = {
                                selectedProductId = product.id
                                expandedProducts = false
                            }
                        )
                    }
                }
            }

            TextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantidade") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            // Date Picker
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                TextField(
                    value = orderDate,
                    onValueChange = { },
                    label = { Text("Data do Pedido") },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    readOnly = true
                )
                OutlinedButton(
                    onClick = {
                        showDatePicker(context) { date ->
                            orderDate = date
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Text("...")
                }
            }

            // Time Picker
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                TextField(
                    value = orderTime,
                    onValueChange = { },
                    label = { Text("Hora do Pedido") },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    readOnly = true
                )
                OutlinedButton(
                    onClick = {
                        showTimePicker(context) { time ->
                            orderTime = time
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Text("...")
                }
            }

            TextField(
                value = totalValue,
                onValueChange = { totalValue = it },
                label = { Text("Valor Total") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                singleLine = true
            )

            Button(
                onClick = {
                    if (selectedClientId == 0 || selectedProductId == 0 || quantity.isBlank() || 
                        orderDate.isBlank() || orderTime.isBlank() || totalValue.isBlank()) {
                        // Show validation error
                    } else {
                        try {
                            val quantityValue = quantity.toInt()
                            val totalValueValue = totalValue.toDouble()
                            val order = Order(
                                id = if (isEditing) orderId else 0,
                                clientId = selectedClientId,
                                productId = selectedProductId,
                                quantity = quantityValue,
                                orderDate = orderDate,
                                orderTime = orderTime,
                                totalValue = totalValueValue
                            )
                            if (isEditing) {
                                viewModel.updateOrder(order)
                            } else {
                                viewModel.insertOrder(order)
                            }
                            onBack()
                        } catch (e: Exception) {
                            // Handle parse error
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(if (isEditing) "Atualizar" else "Salvar")
            }
        }
    }
}

private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(date)
        },
        year, month, day
    )
    datePickerDialog.show()
}

private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            onTimeSelected(time)
        },
        hour, minute, true
    )
    timePickerDialog.show()
}

