package com.vyapaar.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
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

    val hideBottomBarRoutes = listOf("splash", "login", "voice_mode")

    navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBar = destination.route !in hideBottomBarRoutes
    }

    val primaryPurple = Color(0xFF5B32E9)
    val inactiveGray = Color(0xFF94A3B8)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    // 1. Home
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(22.dp)) },
                        label = { Text("Home", fontSize = 11.sp) },
                        selected = currentRoute == "home",
                        onClick = {
                            if (currentRoute != "home") {
                                navController.navigate("home") {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = primaryPurple,
                            selectedTextColor = primaryPurple,
                            indicatorColor = Color(0xFFF3EFFF),
                            unselectedIconColor = inactiveGray,
                            unselectedTextColor = inactiveGray
                        )
                    )

                    // 2. AI Advisor
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.SmartToy, contentDescription = "AI Advisor", modifier = Modifier.size(22.dp)) },
                        label = { Text("AI Advisor", fontSize = 11.sp) },
                        selected = currentRoute == "ai_advisor",
                        onClick = {
                            if (currentRoute != "ai_advisor") {
                                navController.navigate("ai_advisor") {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = primaryPurple,
                            selectedTextColor = primaryPurple,
                            indicatorColor = Color(0xFFF3EFFF),
                            unselectedIconColor = inactiveGray,
                            unselectedTextColor = inactiveGray
                        )
                    )

                    // 3. Business
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.BusinessCenter, contentDescription = "Business", modifier = Modifier.size(22.dp)) },
                        label = { Text("Business", fontSize = 11.sp) },
                        selected = currentRoute in listOf("business", "inventory", "suppliers", "collections"),
                        onClick = {
                            if (currentRoute != "business") {
                                navController.navigate("business") {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = primaryPurple,
                            selectedTextColor = primaryPurple,
                            indicatorColor = Color(0xFFF3EFFF),
                            unselectedIconColor = inactiveGray,
                            unselectedTextColor = inactiveGray
                        )
                    )

                    // 4. Insights
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Insights, contentDescription = "Insights", modifier = Modifier.size(22.dp)) },
                        label = { Text("Insights", fontSize = 11.sp) },
                        selected = currentRoute in listOf("insights", "growth_simulator"),
                        onClick = {
                            if (currentRoute != "insights") {
                                navController.navigate("insights") {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = primaryPurple,
                            selectedTextColor = primaryPurple,
                            indicatorColor = Color(0xFFF3EFFF),
                            unselectedIconColor = inactiveGray,
                            unselectedTextColor = inactiveGray
                        )
                    )

                    // 5. More / Profile
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "More", modifier = Modifier.size(22.dp)) },
                        label = { Text("More", fontSize = 11.sp) },
                        selected = currentRoute == "profile",
                        onClick = {
                            if (currentRoute != "profile") {
                                navController.navigate("profile") {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = primaryPurple,
                            selectedTextColor = primaryPurple,
                            indicatorColor = Color(0xFFF3EFFF),
                            unselectedIconColor = inactiveGray,
                            unselectedTextColor = inactiveGray
                        )
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
            // Screen 1: Splash / Welcome
            composable("splash") {
                SplashScreen(
                    onNavigateToLogin = { navController.navigate("login") },
                    onNavigateToHome = { navController.navigate("home") }
                )
            }

            // Screen 2: Login
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }

            // Screen 3: Home Dashboard
            composable("home") {
                HomeDashboardScreen(
                    onNavigateToAiAdvisor = { navController.navigate("ai_advisor") },
                    onNavigateToInsights = { navController.navigate("insights") },
                    onNavigateToInventory = { navController.navigate("inventory") },
                    onNavigateToCollections = { navController.navigate("collections") },
                    onNavigateToGrowthSimulator = { navController.navigate("growth_simulator") },
                    onNavigateToProfile = { navController.navigate("profile") }
                )
            }

            // Screen 4: AI Advisor (Chat + Voice)
            composable("ai_advisor") {
                AIAdvisorScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToVoiceMode = { navController.navigate("voice_mode") },
                    onNavigateToReport = { route -> navController.navigate(route) }
                )
            }

            // Screen 5: AI Advisor (Voice Mode)
            composable("voice_mode") {
                VoiceModeScreen(
                    onDismiss = { navController.popBackStack() }
                )
            }

            // Screen 6: Business Operations
            composable("business") {
                BusinessOperationsScreen(
                    onNavigateToInventory = { navController.navigate("inventory") },
                    onNavigateToSuppliers = { navController.navigate("suppliers") },
                    onNavigateToCollections = { navController.navigate("collections") }
                )
            }

            // Screen 7: Inventory Management
            composable("inventory") {
                InventoryScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Screen 8: Supplier Intelligence
            composable("suppliers") {
                SupplierIntelligenceScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Screen 9: Growth Simulator
            composable("growth_simulator") {
                GrowthSimulatorScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Screen 10: Collections
            composable("collections") {
                CollectionsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Screen 11: Analytics & Insights
            composable("insights") {
                InsightsScreen(
                    onNavigateToInventory = { navController.navigate("inventory") }
                )
            }

            // Screen 12: Profile & Settings
            composable("profile") {
                ProfileScreen(
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
