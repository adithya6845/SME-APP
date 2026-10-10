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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun GrowthSimulatorScreen(
    onNavigateBack: () -> Unit = {}
) {
    var reinvestmentSlider by remember { mutableStateOf(25f) }
    var selectedRecommendationDetail by remember { mutableStateOf<String?>(null) }
    var showFullReportModal by remember { mutableStateOf(false) }

    val primaryPurple = Color(0xFF5B32E9)
    val successGreen = Color(0xFF10B981)

    // Dynamic calculated potential based on slider
    val projectedGrowth = (15f + (reinvestmentSlider * 0.3f)).let { (it * 10).roundToInt() / 10f }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
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
            Column {
                Text(
                    text = "Growth Simulator",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Plan. Improve. Grow.",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Divider(color = Color(0xFFE2E8F0))

        Column(modifier = Modifier.padding(20.dp)) {
            // Main Card: Growth Potential
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Your Business Growth Potential",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF475569)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "+$projectedGrowth%",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = successGreen
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Estimated sales growth (next 6 months)",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = "Growth",
                            tint = successGreen,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Simulation Bar Chart
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val barHeights = listOf(28.dp, 36.dp, 44.dp, 52.dp, 60.dp, 70.dp)
                        barHeights.forEachIndexed { index, height ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .width(36.dp)
                                        .height(height)
                                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                        .background(if (index == 5) successGreen else primaryPurple.copy(alpha = 0.3f + (index * 0.12f)))
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "M${index + 1}",
                                    fontSize = 10.sp,
                                    color = Color(0xFF94A3B8),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Interactive Slider
                    Text(
                        text = "Simulate Profit Reinvestment: ${reinvestmentSlider.toInt()}%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF334155)
                    )
                    Slider(
                        value = reinvestmentSlider,
                        onValueChange = { reinvestmentSlider = it },
                        valueRange = 5f..50f,
                        colors = SliderDefaults.colors(
                            thumbColor = primaryPurple,
                            activeTrackColor = primaryPurple
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Top Recommendations",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Recommendation Items
            RecommendationCard(
                icon = Icons.Default.Category,
                title = "Expand Categories",
                subtitle = "Reinvest profits (25.4% margin)",
                iconBg = Color(0xFFF3E8FF),
                iconColor = Color(0xFF9333EA),
                onClick = { selectedRecommendationDetail = "Expand Categories" }
            )

            Spacer(modifier = Modifier.height(12.dp))

            RecommendationCard(
                icon = Icons.Default.Bolt,
                title = "Faster Collections",
                subtitle = "-10 days payment terms",
                iconBg = Color(0xFFEFF6FF),
                iconColor = Color(0xFF3B82F6),
                onClick = { selectedRecommendationDetail = "Faster Collections" }
            )

            Spacer(modifier = Modifier.height(12.dp))

            RecommendationCard(
                icon = Icons.Default.Handshake,
                title = "Optimize Suppliers",
                subtitle = "Negotiate better terms",
                iconBg = Color(0xFFECFDF5),
                iconColor = Color(0xFF10B981),
                onClick = { selectedRecommendationDetail = "Optimize Suppliers" }
            )

            Spacer(modifier = Modifier.height(30.dp))

            // CTA Button
            Button(
                onClick = { showFullReportModal = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryPurple),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "View Full Report",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Recommendation Detail Dialog
    if (selectedRecommendationDetail != null) {
        AlertDialog(
            onDismissRequest = { selectedRecommendationDetail = null },
            title = { Text(selectedRecommendationDetail!!, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    when (selectedRecommendationDetail) {
                        "Expand Categories" -> {
                            Text("Deploying surplus capital into smart wearables is projected to yield ₹42L incremental margin this quarter.")
                            Text("• Category: Wearables & Audio (Margin: 38-42%)", fontWeight = FontWeight.SemiBold)
                        }
                        "Faster Collections" -> {
                            Text("Offering a 1.5% instant settlement discount can accelerate cash recovery by 10 days.")
                            Text("• Working Capital freed: ₹85 Lakhs", fontWeight = FontWeight.SemiBold)
                        }
                        "Optimize Suppliers" -> {
                            Text("Switching high-volume orders to Devi and Sons saves ₹40 per unit on average.")
                            Text("• Annual cost saving: ₹12.5 Lakhs", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedRecommendationDetail = null },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Got it")
                }
            }
        )
    }

    // Full Report Modal
    if (showFullReportModal) {
        AlertDialog(
            onDismissRequest = { showFullReportModal = false },
            title = { Text("6-Month Strategic Growth Report", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("• Current Base Monthly Run Rate: ₹20.19 Crore", fontSize = 13.sp)
                    Text("• Simulated 6-Month Projection: ₹24.71 Crore (+${projectedGrowth}%)", color = successGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("• Target Gross Margin Expansion: +2.8%", fontSize = 13.sp)
                    Text("• Risk Index: Low (Supported by healthy debtor churn)", fontSize = 13.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showFullReportModal = false },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
                ) {
                    Text("Download PDF")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFullReportModal = false }) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Composable
fun RecommendationCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconBg: Color,
    iconColor: Color,
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
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 15.sp,
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
}
