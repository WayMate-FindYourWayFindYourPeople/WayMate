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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.waymate.app.components.*
import com.waymate.app.theme.*

@Composable
fun HomeDashboardScreen(
    onSelectRideMode: (isShared: Boolean) -> Unit,
    onViewTrips: () -> Unit,
    onViewPayments: () -> Unit,
    onViewProfile: () -> Unit,
    onViewUpcomingPool: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
            .verticalScroll(scrollState)
            .padding(bottom = 80.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryBlack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FlightLand,
                        contentDescription = null,
                        tint = OnPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "WayMate",
                    style = MaterialTheme.typography.titleMedium,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            // Profile Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceSecondary)
                    .border(1.dp, BorderSubtle, CircleShape)
                    .clickable { onViewProfile() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = OnSurface,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Greeting
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Hello, Aarav",
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Your journey, shared smarter.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryText
                )
            }

            // Ride Mode Selection Cards
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                WayMateInteractiveCard(
                    title = "Shared Ride",
                    subtitle = "From ₹400 / seat · Save up to 66%",
                    description = "Find passengers landing at your airport and heading towards similar destinations.",
                    icon = Icons.Default.Group,
                    isPrimary = true,
                    onClick = { onSelectRideMode(true) }
                )

                WayMateInteractiveCard(
                    title = "Solo Ride",
                    subtitle = "Dedicated cab · Private travel",
                    description = "Book a dedicated airport cab with no passenger sharing.",
                    icon = Icons.Default.DirectionsCar,
                    isPrimary = false,
                    onClick = { onSelectRideMode(false) }
                )
            }

            // Quick Action Shortcuts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionItem(
                    label = "My Trips",
                    icon = Icons.Default.Luggage,
                    onClick = onViewTrips,
                    modifier = Modifier.weight(1f)
                )
                QuickActionItem(
                    label = "Payments",
                    icon = Icons.Default.AccountBalanceWallet,
                    onClick = onViewPayments,
                    modifier = Modifier.weight(1f)
                )
                QuickActionItem(
                    label = "Support",
                    icon = Icons.Default.SupportAgent,
                    onClick = onViewProfile,
                    modifier = Modifier.weight(1f)
                )
            }

            // Upcoming Journey Section
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upcoming journey",
                        style = MaterialTheme.typography.titleMedium,
                        color = OnSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "View pool",
                        style = MaterialTheme.typography.labelSmall,
                        color = SecondaryText,
                        modifier = Modifier.clickable { onViewUpcomingPool() }
                    )
                }

                WayMateCard(
                    modifier = Modifier.clickable { onViewUpcomingPool() }
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "AI 696 · DEL T3",
                                style = MonoSmall,
                                color = OnSurface,
                                fontWeight = FontWeight.Bold
                            )
                            WayMateBadge(text = "2/4 Matched", isSuccess = true)
                        }
                        Text(
                            text = "Delhi IGI Airport → Noida Sector 62",
                            style = MaterialTheme.typography.titleMedium,
                            color = OnSurface
                        )
                        Text(
                            text = "26 Nov · Arrival 10:30 AM · Current fare ₹600",
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondaryText
                        )
                    }
                }
            }

            // Recent Journeys Section
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Recent journeys",
                    style = MaterialTheme.typography.titleMedium,
                    color = OnSurface,
                    fontWeight = FontWeight.SemiBold
                )

                WayMateCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceSecondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "BLR → Indiranagar",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = OnSurface
                                )
                                Text(
                                    text = "14 Nov · 4-pax Pool · Paid ₹400",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SecondaryText
                                )
                            }
                        }
                        Text(
                            text = "₹400",
                            style = MonoMedium,
                            color = OnSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(76.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceCard)
            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = OnSurface,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = OnSurface
            )
        }
    }
}

