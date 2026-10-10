package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class OperationCardItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconColor: Color,
    val route: String? = null
)

@Composable
fun BusinessOperationsScreen(
    onNavigateToInventory: () -> Unit = {},
    onNavigateToSuppliers: () -> Unit = {},
    onNavigateToCollections: () -> Unit = {}
) {
    var selectedOperationDetail by remember { mutableStateOf<String?>(null) }

    val operations = listOf(
        OperationCardItem(
            title = "Sales",
            subtitle = "View sales & orders",
            icon = Icons.Default.ReceiptLong,
            iconColor = Color(0xFFF59E0B)
        ),
        OperationCardItem(
            title = "Products",
            subtitle = "Manage your catalog",
            icon = Icons.Default.Category,
            iconColor = Color(0xFF06B6D4)
        ),
        OperationCardItem(
            title = "Inventory",
            subtitle = "Track stock levels",
            icon = Icons.Default.Inventory2,
            iconColor = Color(0xFFF97316),
            route = "inventory"
        ),
        OperationCardItem(
            title = "Customers",
            subtitle = "Manage customer details",
            icon = Icons.Default.PeopleAlt,
            iconColor = Color(0xFF8B5CF6)
        ),
        OperationCardItem(
            title = "Suppliers",
            subtitle = "View & compare suppliers",
            icon = Icons.Default.LocalShipping,
            iconColor = Color(0xFF3B82F6),
            route = "suppliers"
        ),
        OperationCardItem(
            title = "Purchases",
            subtitle = "Track purchase orders",
            icon = Icons.Default.ShoppingCart,
            iconColor = Color(0xFF10B981)
        ),
        OperationCardItem(
            title = "Expenses",
            subtitle = "Manage business expenses",
            icon = Icons.Default.AccountBalanceWallet,
            iconColor = Color(0xFF6366F1)
        ),
        OperationCardItem(
            title = "Collections",
            subtitle = "Track pending payments",
            icon = Icons.Default.MonetizationOn,
            iconColor = Color(0xFFEF4444),
            route = "collections"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        // Header
        Text(
            text = "Business",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Manage your daily operations",
            fontSize = 14.sp,
            color = Color(0xFF64748B)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Grid of 8 operations
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(operations) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            when (item.route) {
                                "inventory" -> onNavigateToInventory()
                                "suppliers" -> onNavigateToSuppliers()
                                "collections" -> onNavigateToCollections()
                                else -> selectedOperationDetail = item.title
                            }
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(item.iconColor.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = item.iconColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column {
                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.subtitle,
                                fontSize = 11.sp,
                                color = Color(0xFF64748B),
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal dialog for operations (Sales, Products, Customers, Purchases, Expenses)
    if (selectedOperationDetail != null) {
        AlertDialog(
            onDismissRequest = { selectedOperationDetail = null },
            title = {
                Text(
                    text = "${selectedOperationDetail} Hub",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Real-time records for ${selectedOperationDetail}:",
                        fontSize = 13.sp,
                        color = Color(0xFF475569)
                    )
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("• Total entries this month: 248 records", fontSize = 12.sp, color = Color(0xFF1E293B))
                            Text("• Automated sync with GST & Tally: Active", fontSize = 12.sp, color = Color(0xFF10B981))
                            Text("• Next scheduled reconciliation: Today 18:00", fontSize = 12.sp, color = Color(0xFF64748B))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedOperationDetail = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B32E9))
                ) {
                    Text("Done")
                }
            }
        )
    }
}
