package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onLogout: () -> Unit = {}
) {
    var activeModal by remember { mutableStateOf<String?>(null) }
    var showLogoutConfirm by remember { mutableStateOf(false) }

    // Notification preferences state
    var whatsappAlertsEnabled by remember { mutableStateOf(true) }
    var stockAlertsEnabled by remember { mutableStateOf(true) }
    var dailySummaryEnabled by remember { mutableStateOf(true) }

    val primaryPurple = Color(0xFF5B32E9)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Top Header
        Text(
            text = "Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // User Profile Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF5B32E9), Color(0xFF9333EA))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AM",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Arjun Mehta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Business Owner",
                        fontSize = 13.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Menu Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                ProfileMenuItem(
                    icon = Icons.Default.Business,
                    title = "Business Information",
                    subtitle = "Manage your business details",
                    onClick = { activeModal = "Business Information" }
                )
                Divider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle = "Alerts and reminders",
                    onClick = { activeModal = "Notifications" }
                )
                Divider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Settings,
                    title = "App Settings",
                    subtitle = "Theme, language, etc.",
                    onClick = { activeModal = "App Settings" }
                )
                Divider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(horizontal = 16.dp))

                ProfileMenuItem(
                    icon = Icons.Default.HelpOutline,
                    title = "Help & Support",
                    subtitle = "Get help when you need it",
                    onClick = { activeModal = "Help & Support" }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Logout Row
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .clickable { showLogoutConfirm = true },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "Logout",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFEF4444)
                )
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
    }

    // Business Info Modal
    if (activeModal == "Business Information") {
        AlertDialog(
            onDismissRequest = { activeModal = null },
            title = { Text("Business Information", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("• Legal Name: Mehta Electronics Pvt Ltd", fontSize = 13.sp, color = Color(0xFF1E293B))
                    Text("• GSTIN: 27AABCU9603R1ZM", fontSize = 13.sp, color = Color(0xFF1E293B))
                    Text("• Sector: Consumer Electronics & Retail", fontSize = 13.sp, color = Color(0xFF1E293B))
                    Text("• Primary Store: Shop #14, Lamington Road, Mumbai", fontSize = 13.sp, color = Color(0xFF1E293B))
                }
            },
            confirmButton = {
                Button(
                    onClick = { activeModal = null },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Save & Close")
                }
            }
        )
    }

    // Notifications Modal
    if (activeModal == "Notifications") {
        AlertDialog(
            onDismissRequest = { activeModal = null },
            title = { Text("Notification Preferences", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("WhatsApp Payment Reminders", fontSize = 13.sp)
                        Switch(checked = whatsappAlertsEnabled, onCheckedChange = { whatsappAlertsEnabled = it })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Low Stock Threshold Alerts", fontSize = 13.sp)
                        Switch(checked = stockAlertsEnabled, onCheckedChange = { stockAlertsEnabled = it })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Daily P&L Close Summary", fontSize = 13.sp)
                        Switch(checked = dailySummaryEnabled, onCheckedChange = { dailySummaryEnabled = it })
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { activeModal = null },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Save")
                }
            }
        )
    }

    // App Settings Modal
    if (activeModal == "App Settings") {
        AlertDialog(
            onDismissRequest = { activeModal = null },
            title = { Text("App Settings", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("• Currency: Indian Rupee (₹)", fontSize = 13.sp)
                    Text("• Language: English (Indian)", fontSize = 13.sp)
                    Text("• Number System: Lakh / Crore (Indian)", fontSize = 13.sp)
                    Text("• App Version: 1.0.0 (Production)", fontSize = 13.sp, color = Color.Gray)
                }
            },
            confirmButton = {
                Button(
                    onClick = { activeModal = null },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Done")
                }
            }
        )
    }

    // Help & Support Modal
    if (activeModal == "Help & Support") {
        AlertDialog(
            onDismissRequest = { activeModal = null },
            title = { Text("Help & Support", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Need help configuring Vyapaar AI?", fontWeight = FontWeight.SemiBold)
                    Text("• 24x7 Priority Desk: +91 1800-VYAPAAR")
                    Text("• Support Email: support@vyapaar.ai")
                    Text("• Dedicated SME Account Manager: Rahul Verma")
                }
            },
            confirmButton = {
                Button(
                    onClick = { activeModal = null },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Close")
                }
            }
        )
    }

    // Logout Confirmation Dialog
    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text("Confirm Logout", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to log out of Vyapaar AI?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                ) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF1F5F9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFF5B32E9),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF94A3B8)
        )
    }
}
