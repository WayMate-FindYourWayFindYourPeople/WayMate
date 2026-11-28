package com.waymate.app.screens

import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waymate.app.components.*
import com.waymate.app.theme.*

@Composable
fun FindingPoolScreen(
    onCancel: () -> Unit,
    onPoolFound: () -> Unit
) {
    // Pulse animation for radar scanning
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "radar"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
            .padding(horizontal = 20.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Back
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = onCancel,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(SurfaceCard)
                    .border(1.dp, BorderSubtle, CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel", tint = OnSurface)
            }
        }

        // Radar Scanning Visual
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .scale(pulseScale)
                        .clip(CircleShape)
                        .background(PrimaryBlack.copy(alpha = 0.08f))
                )
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .scale(pulseScale * 0.9f)
                        .clip(CircleShape)
                        .background(PrimaryBlack.copy(alpha = 0.15f))
                )
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FlightLand,
                        contentDescription = null,
                        tint = OnPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Finding Your Pool...",
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Scanning co-passengers landing at DEL T3 heading towards Noida corridor.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryText,
                    textAlign = TextAlign.Center
                )
            }

            // Live Checkpoints
            WayMateCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                        Text(text = "Flight AI 696 verified on time", style = MaterialTheme.typography.bodyMedium)
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                        Text(text = "Matching luggage boot capacity", style = MaterialTheme.typography.bodyMedium)
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = PrimaryBlack,
                            strokeWidth = 2.dp
                        )
                        Text(text = "Optimising 3-drop route sequence", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WayMatePrimaryButton(
                text = "Pool Found! View Details",
                onClick = onPoolFound,
                leadingIcon = Icons.Default.CheckCircle
            )
            WayMateSecondaryButton(
                text = "Cancel Search",
                onClick = onCancel
            )
        }
    }
}

@Composable
fun PoolAvailableScreen(
    onBack: () -> Unit,
    onViewVehicle: () -> Unit,
    onViewPassengers: () -> Unit,
    onViewDriver: () -> Unit,
    onConfirmPool: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(
            title = "Pool Available",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Header with Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "3/4 Passengers Matched",
                            style = MaterialTheme.typography.headlineLarge,
                            color = OnSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "High route overlap (88%)",
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondaryText
                        )
                    }
                    WayMateBadge(text = "Save 66%", isSuccess = true)
                }

                // Fare Comparison Card
                WayMateCard(backgroundColor = PrimaryBlack) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Your Pool Fare",
                                style = MaterialTheme.typography.bodySmall,
                                color = SurfaceSecondary
                            )
                            Text(
                                text = "₹400",
                                style = MonoMedium.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                                color = OnPrimary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Solo Cab Fare",
                                style = MaterialTheme.typography.bodySmall,
                                color = SurfaceSecondary
                            )
                            Text(
                                text = "₹1,200",
                                style = MonoMedium.copy(fontSize = 18.sp),
                                color = SurfaceSecondary
                            )
                        }
                    }
                }

                // Route Overview Card
                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Optimized Airport Route",
                            style = MaterialTheme.typography.titleMedium,
                            color = OnSurface
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.TripOrigin, contentDescription = null, tint = PrimaryBlack)
                            Text(text = "Pickup: DEL Terminal 3 (Pillar 12B)", style = MaterialTheme.typography.bodyMedium)
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = SuccessGreen)
                            Text(text = "Drop: Noida Sector 62 (Your Destination)", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                // Sub-detail Navigation Tiles
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    WayMateCard(modifier = Modifier.clickable { onViewVehicle() }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(imageVector = Icons.Default.DirectionsCar, contentDescription = null)
                                Column {
                                    Text(text = "Vehicle: Maruti Ertiga VXi", style = MaterialTheme.typography.titleMedium)
                                    Text(text = "DL 1Z AB 4592 · 6-Seater", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                                }
                            }
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }

                    WayMateCard(modifier = Modifier.clickable { onViewPassengers() }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(imageVector = Icons.Default.People, contentDescription = null)
                                Column {
                                    Text(text = "Co-Passengers: 3 Verified Travellers", style = MaterialTheme.typography.titleMedium)
                                    Text(text = "DEL T3 Arrivals · Same Terminal", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                                }
                            }
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }

                    WayMateCard(modifier = Modifier.clickable { onViewDriver() }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(imageVector = Icons.Default.Badge, contentDescription = null)
                                Column {
                                    Text(text = "Driver: Ramesh Kumar", style = MaterialTheme.typography.titleMedium)
                                    Text(text = "4.9 ★ · 2,850+ completed trips", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                                }
                            }
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(
                text = "Join This Pool (₹400)",
                onClick = onConfirmPool,
                leadingIcon = Icons.Default.Check
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun VehicleDetailsScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(title = "Vehicle Details", onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Maruti Suzuki Ertiga",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceSecondary)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = "DL 1Z AB 4592", style = MonoSmall, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(
                            text = "Commercial Taxi Permit · White Colour",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SecondaryText
                        )
                    }
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Vehicle Specifications", style = MaterialTheme.typography.titleMedium)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Passenger Capacity", color = SecondaryText)
                            Text(text = "6 Seats (4 Booked in Pool)", fontWeight = FontWeight.SemiBold)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Boot Luggage Space", color = SecondaryText)
                            Text(text = "550 Litres (Fits 4 Trolleys)", fontWeight = FontWeight.SemiBold)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Air Conditioning", color = SecondaryText)
                            Text(text = "Powerful Rear AC Vents", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(text = "Back to Pool", onClick = onBack)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PoolPassengersScreen(
    onBack: () -> Unit
) {
    val passengers = listOf(
        Triple("Aarav Sharma (You)", "Stop 2 · Noida Sector 62", "Flight AI 696"),
        Triple("Neha Verma", "Stop 1 · Indirapuram, Ghaziabad", "Flight AI 696"),
        Triple("Rohan Patel", "Stop 3 · Greater Noida Knowledge Park", "Flight 6E 2104")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(title = "Co-Passengers", onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = "3 Travellers in Your Pool",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                passengers.forEach { (name, drop, flight) ->
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
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(SurfaceSecondary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                                }
                                Column {
                                    Text(text = name, style = MaterialTheme.typography.titleMedium)
                                    Text(text = drop, style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                                    Text(text = flight, style = MonoSmall, color = SecondaryText)
                                }
                            }
                            WayMateBadge(text = "Govt ID Verified", isSuccess = true)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(text = "Back to Pool", onClick = onBack)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DriverDetailsScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(title = "Driver Details", onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                WayMateCard {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlack),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(36.dp))
                        }
                        Text(text = "Ramesh Kumar", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = PrimaryBlack, modifier = Modifier.size(18.dp))
                            Text(text = "4.9 · 2,850+ Airport Trips", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                // OTP Card
                WayMateCard(backgroundColor = SurfaceCard) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Start Ride OTP", style = MaterialTheme.typography.labelMedium, color = SecondaryText)
                        Text(text = "8291", style = MonoMedium.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold))
                        Text(text = "Share this OTP with driver only upon entering cab", style = MaterialTheme.typography.bodySmall, color = SecondaryText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(text = "Back to Pool", onClick = onBack)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PoolConfirmationScreen(
    onTrackCab: () -> Unit
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
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(46.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Pool Confirmed!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Your ride is scheduled for Delhi IGI Airport.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryText
                )
            }

            WayMateCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Meeting Point",
                        style = MaterialTheme.typography.labelMedium,
                        color = SecondaryText
                    )
                    Text(
                        text = "Terminal 3 · Arrivals Lane 2 · Pillar 12B",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider(color = BorderSubtle, thickness = 1.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Driver Arriving In", color = SecondaryText)
                        Text(text = "12 Minutes", fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Ride Fare", color = SecondaryText)
                        Text(text = "₹400 (Paid at completion)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        WayMatePrimaryButton(
            text = "Track Cab Live",
            onClick = onTrackCab,
            leadingIcon = Icons.Default.Navigation
        )
    }
}

