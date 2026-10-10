package com.vyapaar.ai.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InsightsScreen(
    onNavigateToInventory: () -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf("Overview") }
    var selectedTimeframe by remember { mutableStateOf("This Month") }
    var timeframeMenuExpanded by remember { mutableStateOf(false) }
    var selectedInsightAlert by remember { mutableStateOf<String?>(null) }

    val primaryPurple = Color(0xFF5B32E9)
    val accentGreen = Color(0xFF10B981)
    val accentAmber = Color(0xFFF59E0B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Top Header
        Text(
            text = "Insights",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs: Overview, Sales, Profit, Expenses
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Overview", "Sales", "Profit", "Expenses").forEach { category ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selectedCategory == category) primaryPurple else Color(0xFFF1F5F9))
                        .clickable { selectedCategory = category }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = category,
                        fontSize = 12.sp,
                        fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Medium,
                        color = if (selectedCategory == category) Color.White else Color(0xFF64748B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Revenue & Profit Chart Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Revenue & Profit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )

                    // Timeframe dropdown
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
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF64748B)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = timeframeMenuExpanded,
                            onDismissRequest = { timeframeMenuExpanded = false }
                        ) {
                            listOf("This Month", "Last Quarter", "Year to Date").forEach { item ->
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

                Spacer(modifier = Modifier.height(10.dp))

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(primaryPurple))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Revenue", fontSize = 11.sp, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(accentGreen))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Profit", fontSize = 11.sp, color = Color(0xFF64748B))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Custom Canvas Chart
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    val width = size.width
                    val height = size.height

                    // Grid lines
                    val lineCount = 4
                    for (i in 0..lineCount) {
                        val y = height * (i.toFloat() / lineCount)
                        drawLine(
                            color = Color(0xFFF1F5F9),
                            start = Offset(0f, y),
                            end = Offset(width, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // Revenue smooth curve
                    val revPoints = listOf(
                        Offset(0f, height * 0.75f),
                        Offset(width * 0.25f, height * 0.65f),
                        Offset(width * 0.5f, height * 0.40f),
                        Offset(width * 0.75f, height * 0.30f),
                        Offset(width, height * 0.20f)
                    )

                    val revPath = Path().apply {
                        moveTo(revPoints[0].x, revPoints[0].y)
                        for (i in 0 until revPoints.size - 1) {
                            val p1 = revPoints[i]
                            val p2 = revPoints[i + 1]
                            val cx = (p1.x + p2.x) / 2
                            cubicTo(cx, p1.y, cx, p2.y, p2.x, p2.y)
                        }
                    }

                    // Area fill under curve
                    val fillPath = Path().apply {
                        addPath(revPath)
                        lineTo(width, height)
                        lineTo(0f, height)
                        close()
                    }

                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(primaryPurple.copy(alpha = 0.25f), Color.Transparent),
                            startY = 0f,
                            endY = height
                        )
                    )

                    drawPath(
                        path = revPath,
                        color = primaryPurple,
                        style = Stroke(width = 3.dp.toPx())
                    )

                    // Profit curve
                    val profitPoints = listOf(
                        Offset(0f, height * 0.88f),
                        Offset(width * 0.25f, height * 0.82f),
                        Offset(width * 0.5f, height * 0.68f),
                        Offset(width * 0.75f, height * 0.55f),
                        Offset(width, height * 0.48f)
                    )

                    val profitPath = Path().apply {
                        moveTo(profitPoints[0].x, profitPoints[0].y)
                        for (i in 0 until profitPoints.size - 1) {
                            val p1 = profitPoints[i]
                            val p2 = profitPoints[i + 1]
                            val cx = (p1.x + p2.x) / 2
                            cubicTo(cx, p1.y, cx, p2.y, p2.x, p2.y)
                        }
                    }

                    drawPath(
                        path = profitPath,
                        color = accentGreen,
                        style = Stroke(width = 3.dp.toPx())
                    )

                    // Point markers
                    revPoints.forEach { pt ->
                        drawCircle(color = Color.White, radius = 5.dp.toPx(), center = pt)
                        drawCircle(color = primaryPurple, radius = 3.dp.toPx(), center = pt)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // X-axis date labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("1 Apr", "8 Apr", "15 Apr", "22 Apr", "30 Apr").forEach { date ->
                        Text(
                            text = date,
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Key Insights Section
        Text(
            text = "Key Insights",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(14.dp))

        InsightItemCard(
            icon = Icons.Default.TrendingUp,
            iconColor = accentGreen,
            title = "Sales up by 18.6% this month",
            onClick = { selectedInsightAlert = "Sales performance surged primarily from smart audio peripherals (+42% YoY volume)." }
        )

        Spacer(modifier = Modifier.height(10.dp))

        InsightItemCard(
            icon = Icons.Default.CheckCircle,
            iconColor = primaryPurple,
            title = "Profit margin improved to 15.4%",
            onClick = { selectedInsightAlert = "Lower raw procurement costs negotiated with bulk suppliers expanded gross spread." }
        )

        Spacer(modifier = Modifier.height(10.dp))

        InsightItemCard(
            icon = Icons.Default.Warning,
            iconColor = accentAmber,
            title = "Low stock alert: Power Bank (3 units)",
            onClick = { onNavigateToInventory() }
        )

        Spacer(modifier = Modifier.height(40.dp))
    }

    // Insight Alert Dialog
    if (selectedInsightAlert != null) {
        AlertDialog(
            onDismissRequest = { selectedInsightAlert = null },
            title = { Text("AI Insight Detail", fontWeight = FontWeight.Bold) },
            text = { Text(selectedInsightAlert!!, fontSize = 14.sp, color = Color(0xFF334155)) },
            confirmButton = {
                Button(
                    onClick = { selectedInsightAlert = null },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Understood")
                }
            }
        )
    }
}

@Composable
fun InsightItemCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1E293B)
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF94A3B8)
            )
        }
    }
}
