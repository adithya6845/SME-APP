package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SupplierInfo(
    val name: String,
    val pricePerUnit: String,
    val rating: String,
    val reviews: String,
    val moq: String,
    val tag: String? = null,
    val features: String = "Reliable • Fast Dispatch",
    val deliveryDays: String = "2-3 days"
)

@Composable
fun SupplierIntelligenceScreen(
    onNavigateBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("Recommended") }
    var selectedSupplierDetail by remember { mutableStateOf<SupplierInfo?>(null) }
    var showComparisonModal by remember { mutableStateOf(false) }
    var orderPlacedNotification by remember { mutableStateOf(false) }

    val primaryPurple = Color(0xFF5B32E9)

    val suppliers = listOf(
        SupplierInfo("Devi and Sons", "₹850 / unit", "4.8", "120 reviews", "50 units", "Best Value", "High quality • Reliable • On-time delivery", "2 days"),
        SupplierInfo("Raghavan-Ghosh", "₹890 / unit", "4.6", "95 reviews", "100 units", null, "Verified GST • Standard packaging", "3 days"),
        SupplierInfo("Chhabra Ltd", "₹920 / unit", "4.4", "64 reviews", "25 units", null, "Flexible credit • Free shipping", "4 days"),
        SupplierInfo("Bumb Group", "₹950 / unit", "4.2", "48 reviews", "10 units", null, "Local warehouse • Immediate pickup", "1 day")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // Top Bar
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
            Column {
                Text(
                    text = "Supplier Intelligence",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Find better suppliers, lower costs",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Divider(color = Color(0xFFE2E8F0))

        Column(modifier = Modifier.padding(16.dp)) {
            // Tabs: Recommended, All Suppliers, Comparison
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Recommended", "All Suppliers", "Comparison").forEach { tab ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (selectedTab == tab) primaryPurple else Color(0xFFF1F5F9))
                            .clickable {
                                selectedTab = tab
                                if (tab == "Comparison") showComparisonModal = true
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = tab,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == tab) Color.White else Color(0xFF64748B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Featured Card: Devi and Sons
            val featured = suppliers.first()
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = featured.name,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF10B981).copy(alpha = 0.12f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Best Value",
                                color = Color(0xFF10B981),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = featured.features,
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${featured.rating} (${featured.reviews})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1E293B)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = featured.pricePerUnit,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryPurple
                            )
                            Text(
                                text = "MOQ: ${featured.moq}",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Button(
                            onClick = { selectedSupplierDetail = featured },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3EFFF)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "View Details",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryPurple
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Other Suppliers",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Other Suppliers List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                items(suppliers.drop(1)) { supplier ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF1F5F9)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Storefront,
                                        contentDescription = null,
                                        tint = primaryPurple,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = supplier.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF0F172A)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = supplier.pricePerUnit,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF64748B)
                                        )
                                        Text(" • ", fontSize = 12.sp, color = Color(0xFF94A3B8))
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color(0xFFF59E0B),
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = " ${supplier.rating}",
                                            fontSize = 12.sp,
                                            color = Color(0xFF64748B)
                                        )
                                    }
                                }
                            }

                            OutlinedButton(
                                onClick = { showComparisonModal = true },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Text(
                                    text = "Compare",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = primaryPurple
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Supplier Details Modal
    if (selectedSupplierDetail != null) {
        val s = selectedSupplierDetail!!
        AlertDialog(
            onDismissRequest = { selectedSupplierDetail = null },
            title = {
                Text(s.name, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("• Price: ${s.pricePerUnit}", fontWeight = FontWeight.Medium)
                    Text("• Minimum Order Quantity (MOQ): ${s.moq}")
                    Text("• Estimated Lead Time: ${s.deliveryDays}")
                    Text("• Rating: ${s.rating} (${s.reviews})")
                    Text("• Features: ${s.features}")
                    if (orderPlacedNotification) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Purchase order request sent to ${s.name}!",
                            color = Color(0xFF10B981),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        orderPlacedNotification = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Request Quote")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    selectedSupplierDetail = null
                    orderPlacedNotification = false
                }) {
                    Text("Close")
                }
            }
        )
    }

    // Comparison Modal
    if (showComparisonModal) {
        AlertDialog(
            onDismissRequest = { showComparisonModal = false },
            title = { Text("Supplier Comparison Matrix", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    suppliers.forEach { supp ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF1F5F9))
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(supp.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("Lead: ${supp.deliveryDays} • MOQ: ${supp.moq}", fontSize = 11.sp, color = Color.Gray)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(supp.pricePerUnit, fontWeight = FontWeight.Bold, color = primaryPurple, fontSize = 13.sp)
                                Text("★ ${supp.rating}", fontSize = 11.sp, color = Color(0xFFF59E0B))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showComparisonModal = false },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Done")
                }
            }
        )
    }
}
