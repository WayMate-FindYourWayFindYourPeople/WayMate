package com.waymate.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waymate.app.components.*
import com.waymate.app.theme.*

@Composable
fun LiveTrackingScreen(
    onBack: () -> Unit,
    onStartJourney: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(title = "Live Cab Tracking", onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Simulated Map Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceSecondary)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlack),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = OnPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        text = "Cab Arriving at Pillar 12B",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ETA: 6 Minutes · 1.4 km away",
                        style = MonoSmall,
                        color = SecondaryText
                    )
                }
            }

            // Driver & OTP Bottom Sheet Card
            WayMateCard {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
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
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlack),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = OnPrimary)
                            }
                            Column {
                                Text(text = "Ramesh Kumar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text(text = "Maruti Ertiga · DL 1Z AB 4592", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceSecondary)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(text = "OTP: 8291", style = MonoMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }

                    HorizontalDivider(color = BorderSubtle, thickness = 1.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {},
                            modifier = Modifier.weight(1f).height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
                        ) {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Call Driver", style = MaterialTheme.typography.labelMedium)
                        }
                        OutlinedButton(
                            onClick = {},
                            modifier = Modifier.weight(1f).height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorTerracotta),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ErrorTerracotta.copy(alpha = 0.5f))
                        ) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "SOS Alert", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            WayMatePrimaryButton(
                text = "Simulate Boarding & Start Ride",
                onClick = onStartJourney,
                leadingIcon = Icons.Default.PlayArrow
            )
        }
    }
}

@Composable
fun JourneyProgressScreen(
    onReachDestination: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Top Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Journey In Progress",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                WayMateBadge(text = "Live Tracking", isSuccess = true)
            }

            // Route Stops Progression
            WayMateCard {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(text = "Route Sequence", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                        Column {
                            Text(text = "DEL Airport Terminal 3", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Picked up all 3 passengers", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Navigation, contentDescription = null, tint = PrimaryBlack)
                        Column {
                            Text(text = "Stop 1: Indirapuram (Neha Verma)", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Arriving in 14 mins · DND Flyway", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.RadioButtonUnchecked, contentDescription = null, tint = SecondaryText)
                        Column {
                            Text(text = "Stop 2: Noida Sector 62 (Your Stop)", style = MaterialTheme.typography.titleMedium)
                            Text(text = "ETA: 32 mins · Final destination", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                        }
                    }
                }
            }

            // Speed & Comfort Card
            WayMateCard(backgroundColor = SurfaceSecondary) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Average Speed", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                        Text(text = "56 km/h", style = MonoMedium.copy(fontWeight = FontWeight.Bold))
                    }
                    Column {
                        Text(text = "Remaining Distance", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                        Text(text = "24 km", style = MonoMedium.copy(fontWeight = FontWeight.Bold))
                    }
                    Column {
                        Text(text = "Quiet Ride", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                        Text(text = "Active", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        WayMatePrimaryButton(
            text = "Simulate Reaching Destination",
            onClick = onReachDestination,
            leadingIcon = Icons.Default.CheckCircle
        )
    }
}

@Composable
fun RideCompletedScreen(
    onProceedToPayment: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
            .padding(horizontal = 20.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(SuccessContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(44.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "You Have Arrived!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Noida Sector 62, Electronic City",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryText
                )
            }

            WayMateCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Trip Summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Total Distance", color = SecondaryText)
                        Text(text = "42.8 km", fontWeight = FontWeight.SemiBold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Duration", color = SecondaryText)
                        Text(text = "38 Minutes", fontWeight = FontWeight.SemiBold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Amount Due", color = SecondaryText)
                        Text(text = "₹400", style = MonoMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold))
                    }
                }
            }

            WayMateCard(backgroundColor = SurfaceSecondary) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Savings, contentDescription = null, tint = PrimaryBlack)
                    Text(
                        text = "You saved ₹800 by sharing this airport ride with WayMate!",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        WayMatePrimaryButton(
            text = "Proceed to Payment (₹400)",
            onClick = onProceedToPayment,
            trailingIcon = Icons.Default.ArrowForward
        )
    }
}

