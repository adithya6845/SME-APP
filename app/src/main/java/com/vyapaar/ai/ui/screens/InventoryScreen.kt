package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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

data class InventoryItem(
    val id: String,
    val name: String,
    val sku: String,
    var stock: Int,
    val icon: ImageVector,
    val price: String = "₹999"
) {
    val status: String
        get() = when {
            stock == 0 -> "Out of Stock"
            stock < 5 -> "Low Stock"
            else -> "In Stock"
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    onNavigateBack: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var showAddProductDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<InventoryItem?>(null) }

    val primaryPurple = Color(0xFF5B32E9)

    val products = remember {
        mutableStateListOf(
            InventoryItem("1", "Wireless Earphones", "SKU: WE001", 45, Icons.Default.Headphones, "₹850"),
            InventoryItem("2", "Smart Watch", "SKU: SW002", 12, Icons.Default.Watch, "₹1,800"),
            InventoryItem("3", "Power Bank", "SKU: PB003", 3, Icons.Default.BatteryChargingFull, "₹1,150"),
            InventoryItem("4", "Charger Cable", "SKU: CC004", 0, Icons.Default.Cable, "₹250"),
            InventoryItem("5", "Bluetooth Speaker", "SKU: BS005", 28, Icons.Default.Speaker, "₹1,499"),
            InventoryItem("6", "USB-C Adapter", "SKU: UA006", 2, Icons.Default.Power, "₹399")
        )
    }

    val inStockCount = products.count { it.stock >= 5 }
    val lowStockCount = products.count { it.stock in 1..4 }
    val outStockCount = products.count { it.stock == 0 }

    val filteredProducts = products.filter { item ->
        val matchesSearch = item.name.contains(searchQuery, ignoreCase = true) ||
                item.sku.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "In Stock" -> item.stock >= 5
            "Low Stock" -> item.stock in 1..4
            "Out of Stock" -> item.stock == 0
            else -> true
        }
        matchesSearch && matchesFilter
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddProductDialog = true },
                containerColor = primaryPurple,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Product", modifier = Modifier.size(28.dp))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(padding)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF0F172A)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Inventory",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }

            Divider(color = Color(0xFFE2E8F0))

            Column(modifier = Modifier.padding(16.dp)) {
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search products...", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF94A3B8)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryPurple,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Filter Pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterPill(
                        label = "All (${products.size})",
                        isSelected = selectedFilter == "All",
                        onClick = { selectedFilter = "All" }
                    )
                    FilterPill(
                        label = "In Stock ($inStockCount)",
                        isSelected = selectedFilter == "In Stock",
                        onClick = { selectedFilter = "In Stock" }
                    )
                    FilterPill(
                        label = "Low Stock ($lowStockCount)",
                        isSelected = selectedFilter == "Low Stock",
                        onClick = { selectedFilter = "Low Stock" }
                    )
                    FilterPill(
                        label = "Out of Stock ($outStockCount)",
                        isSelected = selectedFilter == "Out of Stock",
                        onClick = { selectedFilter = "Out of Stock" }
                    )
                }
            }

            // Products List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(filteredProducts, key = { it.id }) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { editingItem = item },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF1F5F9)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.name,
                                        tint = primaryPurple,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = item.name,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF0F172A)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${item.sku} • Stock: ${item.stock}",
                                        fontSize = 12.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                            }

                            // Status badge
                            val badgeColor = when (item.status) {
                                "In Stock" -> Color(0xFF10B981)
                                "Low Stock" -> Color(0xFFF59E0B)
                                else -> Color(0xFFEF4444)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(badgeColor.copy(alpha = 0.12f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = item.status,
                                    color = badgeColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Product Dialog
    if (showAddProductDialog) {
        var newName by remember { mutableStateOf("") }
        var newSku by remember { mutableStateOf("SKU: ") }
        var newStock by remember { mutableStateOf("10") }

        AlertDialog(
            onDismissRequest = { showAddProductDialog = false },
            title = { Text("Add New Product", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Product Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newSku,
                        onValueChange = { newSku = it },
                        label = { Text("SKU Code") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newStock,
                        onValueChange = { newStock = it },
                        label = { Text("Initial Stock Count") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newName.isNotBlank()) {
                            val count = newStock.toIntOrNull() ?: 1
                            products.add(
                                InventoryItem(
                                    id = System.currentTimeMillis().toString(),
                                    name = newName,
                                    sku = newSku,
                                    stock = count,
                                    icon = Icons.Default.Category
                                )
                            )
                        }
                        showAddProductDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Add Product")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddProductDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Edit Product Stock Dialog
    if (editingItem != null) {
        val currentItem = editingItem!!
        AlertDialog(
            onDismissRequest = { editingItem = null },
            title = { Text(currentItem.name, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("${currentItem.sku} • Unit Price: ${currentItem.price}", color = Color.Gray, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Adjust Quantity in Hand:", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = {
                            if (currentItem.stock > 0) currentItem.stock -= 1
                        }) {
                            Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Decrease", tint = Color.Red)
                        }
                        Text(
                            text = "${currentItem.stock}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        IconButton(onClick = { currentItem.stock += 1 }) {
                            Icon(Icons.Default.AddCircleOutline, contentDescription = "Increase", tint = Color(0xFF10B981))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { editingItem = null },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Save")
                }
            }
        )
    }
}

@Composable
fun FilterPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Color(0xFF5B32E9) else Color(0xFFF1F5F9))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF64748B)
        )
    }
}
