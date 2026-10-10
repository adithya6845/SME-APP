package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.background
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

data class DebtorRecord(
    val id: String,
    val name: String,
    var amount: String,
    val daysOverdue: Int,
    val status: String, // "High" or "Medium"
    val phone: String = "+91 98765 43210"
)

@Composable
fun CollectionsScreen(
    onNavigateBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("All") }
    var selectedDebtor by remember { mutableStateOf<DebtorRecord?>(null) }
    var actionFeedbackMessage by remember { mutableStateOf<String?>(null) }

    val primaryPurple = Color(0xFF5B32E9)

    val debtorList = remember {
        mutableStateListOf(
            DebtorRecord("1", "Tejas Som", "₹2,06,590", 30, "High"),
            DebtorRecord("2", "Nachiket", "₹1,38,196", 29, "High"),
            DebtorRecord("3", "Anvita", "₹2,16,726", 28, "High"),
            DebtorRecord("4", "Global Traders", "₹98,540", 18, "Medium"),
            DebtorRecord("5", "Sunrise Stores", "₹72,410", 12, "Medium"),
            DebtorRecord("6", "Kalyan Textiles", "₹54,200", 8, "Medium")
        )
    }

    val filteredList = debtorList.filter {
        when (selectedTab) {
            "Overdue" -> it.daysOverdue >= 20
            "At Risk" -> it.status == "High"
            else -> true
        }
    }

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
                    text = "Collections",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Recover pending payments and maintain healthy cash flow.",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Divider(color = Color(0xFFE2E8F0))

        Column(modifier = Modifier.padding(16.dp)) {
            // Stats Row: Total Receivables & Collected
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Total Receivables",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "₹4.33Cr",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1.1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Collected (This Month)",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹2.94Cr",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF10B981)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "(67.8%)",
                                fontSize = 11.sp,
                                color = Color(0xFF10B981),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filter Tabs: All, Overdue, At Risk
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Overdue", "At Risk").forEach { tab ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (selectedTab == tab) primaryPurple else Color(0xFFF1F5F9))
                            .clickable { selectedTab = tab }
                            .padding(horizontal = 16.dp, vertical = 7.dp)
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

            Spacer(modifier = Modifier.height(14.dp))

            // Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Customer", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1.3f))
                Text("Amount", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                Text("Days Overdue", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                Text("Status", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(0.7f))
            }

            // Customer Records List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                items(filteredList, key = { it.id }) { record ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedDebtor = record },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = record.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F172A),
                                modifier = Modifier.weight(1.3f)
                            )
                            Text(
                                text = record.amount,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${record.daysOverdue}",
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                modifier = Modifier.weight(1f)
                            )

                            val statusColor = if (record.status == "High") Color(0xFFEF4444) else Color(0xFFF59E0B)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(statusColor.copy(alpha = 0.12f))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = record.status,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = statusColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Debtor Action Modal
    if (selectedDebtor != null) {
        val debtor = selectedDebtor!!
        AlertDialog(
            onDismissRequest = {
                selectedDebtor = null
                actionFeedbackMessage = null
            },
            title = {
                Text(debtor.name, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Pending Invoice Amount: ${debtor.amount}", fontWeight = FontWeight.SemiBold)
                    Text("Overdue Duration: ${debtor.daysOverdue} days")
                    Text("Contact: ${debtor.phone}", color = Color.Gray)

                    if (actionFeedbackMessage != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            actionFeedbackMessage!!,
                            color = Color(0xFF10B981),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                actionFeedbackMessage = "WhatsApp payment link sent to ${debtor.name}!"
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("WhatsApp", fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                actionFeedbackMessage = "Dialing ${debtor.phone}..."
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = primaryPurple),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Call", fontSize = 12.sp)
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            debtorList.remove(debtor)
                            selectedDebtor = null
                            actionFeedbackMessage = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Mark as Paid", color = Color(0xFF10B981))
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedDebtor = null
                    actionFeedbackMessage = null
                }) {
                    Text("Done")
                }
            }
        )
    }
}
