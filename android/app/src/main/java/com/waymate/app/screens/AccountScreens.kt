package com.waymate.app.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waymate.app.components.*
import com.waymate.app.theme.*

@Composable
fun MyTripsScreen(
    onBack: () -> Unit,
    onViewActiveTrip: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("Active") }
    val tabs = listOf("Active (1)", "Completed (4)")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(title = "My Trips", onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tab Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceSecondary)
                    .padding(4.dp)
            ) {
                tabs.forEach { tabName ->
                    val isSelected = (selectedTab == "Active" && tabName.startsWith("Active")) ||
                            (selectedTab == "Completed" && tabName.startsWith("Completed"))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) PrimaryBlack else Color.Transparent)
                            .clickable {
                                selectedTab = if (tabName.startsWith("Active")) "Active" else "Completed"
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tabName,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isSelected) OnPrimary else SecondaryText,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            if (selectedTab == "Active") {
                // Active Trip Card
                WayMateCard(modifier = Modifier.clickable { onViewActiveTrip() }) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "AI 696 · DEL T3", style = MonoSmall, fontWeight = FontWeight.Bold)
                            WayMateBadge(text = "Pool Matched", isSuccess = true)
                        }
                        Text(
                            text = "Delhi IGI Airport → Noida Sector 62",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Today · 10:30 AM · 3/4 Passengers · Driver Ramesh",
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondaryText
                        )
                        HorizontalDivider(color = BorderSubtle, thickness = 1.dp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Current Fare: ₹400", style = MonoMedium.copy(fontWeight = FontWeight.Bold))
                            Text(text = "Track Ride →", style = MaterialTheme.typography.labelMedium, color = PrimaryBlack)
                        }
                    }
                }
            } else {
                // Completed Trips List
                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "BLR T1 → Indiranagar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "₹400", style = MonoMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Text(text = "14 Nov 2026 · 4-pax Pool · Saved ₹750", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                    }
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "BOM T2 → Bandra West", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "₹350", style = MonoMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Text(text = "28 Oct 2026 · 3-pax Pool · Saved ₹600", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(
    onBack: () -> Unit
) {
    val notificationList = listOf(
        Triple(
            "Flight Landing On Time",
            "Flight AI 696 is scheduled to touch down at DEL Terminal 3 Gate 14 at 10:30 AM.",
            Icons.Default.FlightLand
        ),
        Triple(
            "New Passenger Joined Pool",
            "Neha Verma joined your ride to Noida corridor. Fare decreased to ₹400 per seat.",
            Icons.Default.GroupAdd
        ),
        Triple(
            "Driver Assigned",
            "Ramesh Kumar (Maruti Ertiga DL 1Z AB 4592) assigned. Start OTP is 8291.",
            Icons.Default.DirectionsCar
        ),
        Triple(
            "Payment Receipt",
            "₹400 received for airport transfer BLR to Indiranagar on 14 Nov.",
            Icons.Default.Receipt
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(title = "Notifications", onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            notificationList.forEach { (title, desc, icon) ->
                WayMateCard {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceSecondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = icon, contentDescription = null, tint = PrimaryBlack, modifier = Modifier.size(20.dp))
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = desc, style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(title = "Profile", onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Card
            WayMateCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlack),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(32.dp))
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Aarav Sharma", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text(text = "+91 98765 43210 · aarav.s@gmail.com", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            WayMateBadge(text = "Govt ID Verified", isSuccess = true)
                            WayMateBadge(text = "4.95 ★ Passenger", isSuccess = true)
                        }
                    }
                }
            }

            // Saved Preferences
            WayMateCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Travel Preferences", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Frequent Airports", color = SecondaryText)
                        Text(text = "DEL, BLR, BOM", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Default Conversation", color = SecondaryText)
                        Text(text = "Quiet Ride", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Emergency Contact", color = SecondaryText)
                        Text(text = "+91 98111 22334 (Pooja S.)", fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Total Savings Stat Card
            WayMateCard(backgroundColor = SurfaceSecondary) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Lifetime Pool Savings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = "₹3,450 Saved", style = MonoMedium.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold))
                    Text(text = "Across 5 pooled airport rides compared to solo taxis.", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                }
            }

            // Sign out button
            WayMateSecondaryButton(
                text = "Sign Out",
                onClick = onSignOut,
                leadingIcon = Icons.Default.Logout
            )
        }
    }
}

