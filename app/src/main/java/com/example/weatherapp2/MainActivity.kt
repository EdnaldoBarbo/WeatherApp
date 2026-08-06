package com.example.weatherapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.weatherapp2.api.WeatherService
import com.example.weatherapp2.db.fb.FBDatabase
import com.example.weatherapp2.ui.nav.BottomNavBar
import com.example.weatherapp2.ui.nav.BottomNavItem
import com.example.weatherapp2.ui.nav.MainNavHost
import com.example.weatherapp2.ui.nav.Route
import com.example.weatherapp2.ui.theme.WeatherAPP2Theme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val fbDB = remember { FBDatabase() }
            val weatherService = remember { WeatherService(this) }
            val viewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(fbDB, weatherService)
            )
            val navController = rememberNavController()
            var showDialog by remember { mutableStateOf(false) }

            LaunchedEffect(viewModel.page) {
                navController.navigate(viewModel.page) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = {}
            )

            WeatherAPP2Theme {
                if (showDialog) CityDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = { city ->
                        if (city.isNotBlank()) viewModel.addCity(city)
                        showDialog = false
                    }
                )
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                val name = viewModel.user?.name ?: "[carregando...]"
                                Text("Bem-vindo/a! $name")
                            },
                            actions = {
                                IconButton(onClick = {
                                    Firebase.auth.signOut()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = "Sair"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        val items = listOf(
                            BottomNavItem.HomeButton,
                            BottomNavItem.ListButton,
                            BottomNavItem.MapButton,
                        )
                        BottomNavBar(viewModel = viewModel, items = items)
                    },
                    floatingActionButton = {
                        if (viewModel.page == Route.List) {
                            FloatingActionButton(onClick = { showDialog = true }) {
                                Icon(Icons.Default.Add, contentDescription = "Adicionar")
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        MainNavHost(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
