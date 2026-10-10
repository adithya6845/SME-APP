package com.vyapaar.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.vyapaar.ai.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                VyapaarApp()
            }
        }
    }
}

@Composable
fun VyapaarApp() {
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(false) }

    // Update bottom bar visibility based on route
    navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBar = destination.route !in listOf("splash", "login")
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = Color.White) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = currentRoute == "home",
                        onClick = { navController.navigate("home") },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF5B32E9), selectedTextColor = Color(0xFF5B32E9))
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.SmartToy, contentDescription = "AI Advisor") },
                        label = { Text("AI Advisor") },
                        selected = currentRoute == "ai_advisor",
                        onClick = { /*TODO*/ }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.BusinessCenter, contentDescription = "Business") },
                        label = { Text("Business") },
                        selected = currentRoute == "business",
                        onClick = { /*TODO*/ }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Insights, contentDescription = "Insights") },
                        label = { Text("Insights") },
                        selected = currentRoute == "insights",
                        onClick = { /*TODO*/ }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash") { SplashScreen(onNavigateToLogin = { navController.navigate("login") }) }
            composable("login") { LoginScreen(onLoginSuccess = { navController.navigate("home") }) }
            composable("home") { HomeDashboardScreen() }
        }
    }
}
