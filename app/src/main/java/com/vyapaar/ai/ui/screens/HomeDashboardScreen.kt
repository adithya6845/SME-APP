package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
fun HomeDashboardScreen(
    onNavigateToAiAdvisor: () -> Unit = {},
    onNavigateToInsights: () -> Unit = {},
    onNavigateToInventory: () -> Unit = {},
    onNavigateToCollections: () -> Unit = {},
    onNavigateToGrowthSimulator: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var selectedTimeframe by remember { mutableStateOf("This Month") }
    var timeframeMenuExpanded by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }

    val primaryPurple = Color(0xFF5B32E9)
    val lightPurpleBg = Color(0xFFF3EFFF)
    val greenSuccess = Color(0xFF10B981)
    val orangeWarning = Color(0xFFF59E0B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good Afternoon,",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Arjun! 👋",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    IconButton(onClick = { showNotificationsDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF334155),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    // Notification badge dot
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444))
                            .align(Alignment.TopEnd)
                            .offset(x = (-8).dp, y = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Profile Avatar
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF5B32E9), Color(0xFF9333EA))
                            )
                        )
                        .clickable { onNavigateToProfile() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AM",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Banner Card: "Your business is growing!"
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToGrowthSimulator() },
            colors = CardDefaults.cardColors(containerColor = lightPurpleBg),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Your business is growing!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = primaryPurple
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Revenue increased by 12% this month.",
                        fontSize = 13.sp,
                        color = Color(0xFF475569)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onNavigateToGrowthSimulator,
                        colors = ButtonDefaults.buttonColors(containerColor = primaryPurple),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "View Details",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                // Upward graph icon badge
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(primaryPurple.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = "Growth",
                        tint = primaryPurple,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Key Metrics Header & Timeframe selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Key Metrics",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Box {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { timeframeMenuExpanded = true }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedTimeframe,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Timeframe",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(18.dp)
                    )
                }

                DropdownMenu(
                    expanded = timeframeMenuExpanded,
                    onDismissRequest = { timeframeMenuExpanded = false }
                ) {
                    listOf("This Month", "Last Month", "This Quarter", "Financial Year").forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                selectedTimeframe = item
                                timeframeMenuExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Key Metrics Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Total Revenue",
                value = if (selectedTimeframe == "This Month") "₹20.19Cr" else "₹18.45Cr",
                subtitle = "↑ 15.8% vs last month",
                icon = Icons.Default.CurrencyRupee,
                iconColor = primaryPurple,
                subtitleColor = greenSuccess,
                onClick = onNavigateToInsights
            )
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Net Profit",
                value = if (selectedTimeframe == "This Month") "₹3.11Cr" else "₹2.80Cr",
                subtitle = "↑ 15.4% margin",
                icon = Icons.Default.TrendingUp,
                iconColor = greenSuccess,
                subtitleColor = greenSuccess,
                onClick = onNavigateToInsights
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Pending Receivables",
                value = "₹4.33Cr",
                subtitle = "↑ 14.6% overdue",
                icon = Icons.Default.AccountBalanceWallet,
                iconColor = orangeWarning,
                subtitleColor = orangeWarning,
                onClick = onNavigateToCollections
            )
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Business Health",
                value = "86/100",
                subtitle = "● Good",
                icon = Icons.Default.Speed,
                iconColor = greenSuccess,
                subtitleColor = greenSuccess,
                onClick = onNavigateToGrowthSimulator
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Quick Actions
        Text(
            text = "Quick Actions",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickActionButton(
                icon = Icons.Default.SmartToy,
                label = "Ask AI",
                onClick = onNavigateToAiAdvisor
            )
            QuickActionButton(
                icon = Icons.Default.BarChart,
                label = "View Reports",
                badge = "New",
                onClick = onNavigateToInsights
            )
            QuickActionButton(
                icon = Icons.Default.Inventory2,
                label = "Manage Stock",
                onClick = onNavigateToInventory
            )
            QuickActionButton(
                icon = Icons.Default.ReceiptLong,
                label = "View Collections",
                onClick = onNavigateToCollections
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    // Notifications Dialog
    if (showNotificationsDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationsDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = primaryPurple)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Notifications", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    NotificationItem(
                        title = "Pending Payment Alert",
                        desc = "Tejas Som has ₹2,06,590 pending for 30 days.",
                        time = "10m ago"
                    )
                    NotificationItem(
                        title = "Stock Running Low",
                        desc = "Power Bank PB003 has only 3 units left.",
                        time = "1h ago"
                    )
                    NotificationItem(
                        title = "Growth Target Achieved",
                        desc = "Monthly revenue surpassed ₹20 Crore!",
                        time = "Yesterday"
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showNotificationsDialog = false }) {
                    Text("Close", color = primaryPurple)
                }
            }
        )
    }
}

@Composable
fun NotificationItem(title: String, desc: String, time: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF1F5F9))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF0F172A))
            Text(time, fontSize = 11.sp, color = Color(0xFF94A3B8))
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(desc, fontSize = 12.sp, color = Color(0xFF475569))
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    subtitleColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF64748B)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = subtitleColor
            )
        }
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    badge: String? = null,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, Color(0xFFE2E8F0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF5B32E9),
                modifier = Modifier.size(26.dp)
            )
            if (badge != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-2).dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFEF4444))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badge,
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF334155)
        )
    }
}
