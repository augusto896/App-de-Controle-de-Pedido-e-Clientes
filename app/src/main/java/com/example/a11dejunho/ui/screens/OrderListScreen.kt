package com.example.a11dejunho.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a11dejunho.domain.model.Order
import com.example.a11dejunho.ui.components.ConfirmDeleteDialog
import com.example.a11dejunho.ui.viewmodel.OrderViewModel

import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(
    viewModel: OrderViewModel,
    onBack: () -> Unit,
    onNavigateToEditOrder: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var orderToDelete by remember { mutableStateOf<Order?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedidos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToEditOrder(-1) }) {
                Icon(Icons.Filled.Add, "Novo Pedido")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = { Text("Buscar pedido...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (uiState.orders.isEmpty()) {
                Text(
                    text = "Nenhum pedido cadastrado",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn {
                    items(uiState.orders) { order ->
                        OrderCard(
                            order = order,
                            clients = uiState.clients,
                            products = uiState.products,
                            onEdit = { onNavigateToEditOrder(order.id) },
                            onDelete = {
                                orderToDelete = order
                                showDeleteDialog = true
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            if (showDeleteDialog && orderToDelete != null) {
                ConfirmDeleteDialog(
                    onConfirm = {
                        viewModel.deleteOrder(orderToDelete!!)
                        showDeleteDialog = false
                        orderToDelete = null
                    },
                    onDismiss = {
                        showDeleteDialog = false
                        orderToDelete = null
                    },
                    title = "Confirmar exclusão",
                    message = "Deseja realmente excluir este pedido?"
                )
            }
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    clients: List<com.example.a11dejunho.domain.model.Client>,
    products: List<com.example.a11dejunho.domain.model.Product>,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val clientName = clients.find { it.id == order.clientId }?.name ?: "Cliente desconhecido"
    val productName = products.find { it.id == order.productId }?.name ?: "Produto desconhecido"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = clientName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = productName,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                IconButton(onClick = onEdit, modifier = Modifier.width(48.dp)) {
                    Icon(Icons.Filled.Edit, "Editar")
                }
                IconButton(onClick = onDelete, modifier = Modifier.width(48.dp)) {
                    Icon(Icons.Filled.Delete, "Deletar")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Qtd: ${order.quantity}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${order.orderDate} ${order.orderTime}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Total: R$ ${"%.2f".format(order.totalValue)}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

