package com.example.a11dejunho.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.a11dejunho.data.database.AppDatabase
import com.example.a11dejunho.data.repository.ClientRepository
import com.example.a11dejunho.data.repository.OrderRepository
import com.example.a11dejunho.data.repository.ProductRepository
import com.example.a11dejunho.data.repository.SettingsRepository
import com.example.a11dejunho.data.repository.UserRepository
import com.example.a11dejunho.ui.screens.ClientFormScreen
import com.example.a11dejunho.ui.screens.ClientListScreen
import com.example.a11dejunho.ui.screens.HomeScreen
import com.example.a11dejunho.ui.screens.LoginScreen
import com.example.a11dejunho.ui.screens.OrderFormScreen
import com.example.a11dejunho.ui.screens.OrderListScreen
import com.example.a11dejunho.ui.screens.ProductFormScreen
import com.example.a11dejunho.ui.screens.ProductListScreen
import com.example.a11dejunho.ui.screens.SettingsScreen
import com.example.a11dejunho.ui.viewmodel.ClientViewModel
import com.example.a11dejunho.ui.viewmodel.LoginViewModel
import com.example.a11dejunho.ui.viewmodel.OrderViewModel
import com.example.a11dejunho.ui.viewmodel.ProductViewModel
import com.example.a11dejunho.ui.viewmodel.SettingsViewModel
import android.content.Context

@Composable
fun AppNavigation(navController: NavHostController, context: Context) {
    val database = remember { AppDatabase.getInstance(context) }
    val userRepository = remember { UserRepository(database.userDao()) }
    val clientRepository = remember { ClientRepository(database.clientDao()) }
    val productRepository = remember { ProductRepository(database.productDao()) }
    val orderRepository = remember { OrderRepository(database.orderDao()) }
    val settingsRepository = remember { SettingsRepository(context) }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            val viewModel = remember { LoginViewModel(userRepository) }
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { userId ->
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onNavigateToClients = { navController.navigate("clients") },
                onNavigateToProducts = { navController.navigate("products") },
                onNavigateToOrders = { navController.navigate("orders") },
                onNavigateToSettings = { navController.navigate("settings") },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        composable("clients") {
            val viewModel = remember { ClientViewModel(clientRepository) }
            ClientListScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToEditClient = { clientId ->
                    navController.navigate("client_form/$clientId")
                }
            )
        }

        composable(
            "client_form/{clientId}",
            arguments = listOf(navArgument("clientId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clientId = backStackEntry.arguments?.getInt("clientId") ?: -1
            val viewModel = remember { ClientViewModel(clientRepository) }
            ClientFormScreen(
                viewModel = viewModel,
                clientId = clientId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("products") {
            val viewModel = remember { ProductViewModel(productRepository) }
            ProductListScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToEditProduct = { productId ->
                    navController.navigate("product_form/$productId")
                }
            )
        }

        composable(
            "product_form/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: -1
            val viewModel = remember { ProductViewModel(productRepository) }
            ProductFormScreen(
                viewModel = viewModel,
                productId = productId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("orders") {
            val viewModel = remember {
                OrderViewModel(orderRepository, clientRepository, productRepository)
            }
            OrderListScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToEditOrder = { orderId ->
                    navController.navigate("order_form/$orderId")
                }
            )
        }

        composable(
            "order_form/{orderId}",
            arguments = listOf(navArgument("orderId") { type = NavType.IntType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getInt("orderId") ?: -1
            val viewModel = remember {
                OrderViewModel(orderRepository, clientRepository, productRepository)
            }
            OrderFormScreen(
                viewModel = viewModel,
                orderId = orderId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("settings") {
            val viewModel = remember { SettingsViewModel(settingsRepository) }
            SettingsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

