package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeDashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Good Afternoon,", fontSize = 16.sp, color = Color.Gray)
                Text("Arjun! 👋", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E1E1E))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEEE8FF)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Your business is growing!", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF5B32E9))
                Spacer(modifier = Modifier.height(4.dp))
                Text("Revenue increased by 12% this month.", fontSize = 14.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B32E9)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("View Details")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Key Metrics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Key Metrics", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("This Month ▼", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MetricCard(modifier = Modifier.weight(1f), title = "Total Revenue", value = "₹20.19Cr", subtitle = "↑ 18.8%", icon = Icons.Default.CurrencyRupee, color = Color(0xFF5B32E9))
            MetricCard(modifier = Modifier.weight(1f), title = "Net Profit", value = "₹3.11Cr", subtitle = "↑ 15.4%", icon = Icons.Default.TrendingUp, color = Color(0xFF00C853))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MetricCard(modifier = Modifier.weight(1f), title = "Pending", value = "₹4.33Cr", subtitle = "↑ 14.6%", icon = Icons.Default.AccountBalanceWallet, color = Color(0xFFFF9800))
            MetricCard(modifier = Modifier.weight(1f), title = "Business Health", value = "86/100", subtitle = "Good", icon = Icons.Default.Favorite, color = Color(0xFF00C853))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Actions
        Text("Quick Actions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickActionItem(icon = Icons.Default.SmartToy, label = "Ask AI")
            QuickActionItem(icon = Icons.Default.BarChart, label = "Reports")
            QuickActionItem(icon = Icons.Default.Inventory, label = "Stock")
            QuickActionItem(icon = Icons.Default.Receipt, label = "Collections")
        }
        
        Spacer(modifier = Modifier.height(80.dp)) // padding for bottom nav
    }
}

@Composable
fun MetricCard(modifier: Modifier = Modifier, title: String, value: String, subtitle: String, icon: ImageVector, color: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = 12.sp, color = color)
        }
    }
}

@Composable
fun QuickActionItem(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color.White, CircleShape)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = Color(0xFF5B32E9))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontSize = 12.sp, color = Color.DarkGray)
    }
}
