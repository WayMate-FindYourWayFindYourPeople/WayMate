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
import com.waymate.app.components.*
import com.waymate.app.theme.*

@Composable
fun FlightDetailsScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var flightNumber by remember { mutableStateOf("AI 696") }
    var selectedAirline by remember { mutableStateOf("Air India") }
    var selectedAirport by remember { mutableStateOf("DEL – Indira Gandhi International Airport") }
    var arrivalDate by remember { mutableStateOf("26 Nov 2026") }
    var arrivalTime by remember { mutableStateOf("10:30 AM") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(
            title = "Flight Details",
            onBackClick = onBack,
            stepIndicator = "Step 1 of 5"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Plan Your Airport Ride",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Enter your flight details before boarding. WayMate prepares your pool while you fly.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryText
                    )
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            WayMateTextField(
                                value = flightNumber,
                                onValueChange = { flightNumber = it },
                                label = "Flight number",
                                placeholder = "AI 696",
                                isMonospace = true,
                                modifier = Modifier.weight(1f)
                            )
                            WayMateTextField(
                                value = selectedAirline,
                                onValueChange = { selectedAirline = it },
                                label = "Airline",
                                placeholder = "Air India",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        WayMateTextField(
                            value = selectedAirport,
                            onValueChange = { selectedAirport = it },
                            label = "Arrival airport",
                            leadingIcon = Icons.Default.FlightLand
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            WayMateTextField(
                                value = arrivalDate,
                                onValueChange = { arrivalDate = it },
                                label = "Flight arrival date",
                                modifier = Modifier.weight(1f)
                            )
                            WayMateTextField(
                                value = arrivalTime,
                                onValueChange = { arrivalTime = it },
                                label = "Expected arrival",
                                isMonospace = true,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Flight Sync Guarantee Card
                WayMateCard(backgroundColor = SurfaceSecondary) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = PrimaryBlack,
                            modifier = Modifier.size(24.dp)
                        )
                        Column {
                            Text(
                                text = "Delay Protection Included",
                                style = MaterialTheme.typography.titleMedium,
                                color = OnSurface
                            )
                            Text(
                                text = "If your flight is delayed, your pool automatically reschedules with no penalty.",
                                style = MaterialTheme.typography.bodySmall,
                                color = SecondaryText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(
                text = "Next: Choose Destination",
                onClick = onNext,
                trailingIcon = Icons.Default.ArrowForward
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FinalDestinationScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var pickupSpot by remember { mutableStateOf("DEL Terminal 3 (Arrivals Gate 4)") }
    var destinationAddress by remember { mutableStateOf("Noida Sector 62, Electronic City") }

    val popularDestinations = listOf(
        "Noida Sector 62",
        "Cyber Hub, Gurgaon",
        "Connaught Place",
        "Indirapuram, Ghaziabad",
        "Greater Noida West"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(
            title = "Final Destination",
            onBackClick = onBack,
            stepIndicator = "Step 2 of 5"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Where are you heading?",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "We match you with travelers landing at Terminal 3 taking your highway corridor.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryText
                    )
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        WayMateTextField(
                            value = pickupSpot,
                            onValueChange = { pickupSpot = it },
                            label = "Pickup location",
                            leadingIcon = Icons.Default.TripOrigin
                        )

                        WayMateTextField(
                            value = destinationAddress,
                            onValueChange = { destinationAddress = it },
                            label = "Drop-off destination",
                            leadingIcon = Icons.Default.LocationOn
                        )
                    }
                }

                // Popular Destination Chips
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Popular Airport Routes",
                        style = MaterialTheme.typography.titleMedium,
                        color = OnSurface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        popularDestinations.take(2).forEach { place ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (place == destinationAddress) PrimaryBlack else SurfaceCard)
                                    .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
                                    .clickable { destinationAddress = place }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = place,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (place == destinationAddress) OnPrimary else OnSurface
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(
                text = "Next: Passenger Details",
                onClick = onNext,
                trailingIcon = Icons.Default.ArrowForward
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PassengerDetailsScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var seatsBooked by remember { mutableIntStateOf(1) }
    var selectedGenderPref by remember { mutableStateOf("Any (Standard Pool)") }
    val genderOptions = listOf("Any (Standard Pool)", "Female Only Pool", "Co-ed Group")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(
            title = "Passenger Details",
            onBackClick = onBack,
            stepIndicator = "Step 3 of 5"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Who is travelling?",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Specify seat count and preference to ensure a comfortable group match.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryText
                    )
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Seats required",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = OnSurface
                                )
                                Text(
                                    text = "Max 2 seats per booking in a pool",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SecondaryText
                                )
                            }
                            StepperControl(
                                value = seatsBooked,
                                onValueChange = { seatsBooked = it },
                                min = 1,
                                max = 2
                            )
                        }

                        HorizontalDivider(color = BorderSubtle, thickness = 1.dp)

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Co-passenger preference",
                                style = MaterialTheme.typography.titleMedium,
                                color = OnSurface
                            )
                            genderOptions.forEach { opt ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (opt == selectedGenderPref) SurfaceSecondary else SurfaceBeige)
                                        .border(
                                            1.dp,
                                            if (opt == selectedGenderPref) PrimaryBlack else BorderSubtle,
                                            RoundedCornerShape(10.dp)
                                        )
                                        .clickable { selectedGenderPref = opt }
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = opt,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = OnSurface
                                    )
                                    if (opt == selectedGenderPref) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = PrimaryBlack,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(
                text = "Next: Luggage Details",
                onClick = onNext,
                trailingIcon = Icons.Default.ArrowForward
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LuggageDetailsScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var cabinBags by remember { mutableIntStateOf(1) }
    var checkinBags by remember { mutableIntStateOf(1) }
    var hasOversized by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(
            title = "Luggage Details",
            onBackClick = onBack,
            stepIndicator = "Step 4 of 5"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Luggage Space Guarantee",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "We match vehicle boot space with all pooled passengers' luggage.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryText
                    )
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Cabin Bags
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Backpack,
                                    contentDescription = null,
                                    tint = PrimaryBlack
                                )
                                Column {
                                    Text(
                                        text = "Cabin Bags (Up to 7kg)",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = OnSurface
                                    )
                                    Text(
                                        text = "Fits in footwell or lap",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SecondaryText
                                    )
                                }
                            }
                            StepperControl(
                                value = cabinBags,
                                onValueChange = { cabinBags = it },
                                min = 0,
                                max = 3
                            )
                        }

                        HorizontalDivider(color = BorderSubtle, thickness = 1.dp)

                        // Check-in Luggage
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Luggage,
                                    contentDescription = null,
                                    tint = PrimaryBlack
                                )
                                Column {
                                    Text(
                                        text = "Check-in Trolleys",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = OnSurface
                                    )
                                    Text(
                                        text = "Goes into car boot space",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SecondaryText
                                    )
                                }
                            }
                            StepperControl(
                                value = checkinBags,
                                onValueChange = { checkinBags = it },
                                min = 0,
                                max = 2
                            )
                        }
                    }
                }

                // Estimated Boot Space Visualization
                WayMateCard(backgroundColor = SurfaceSecondary) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Vehicle Boot Space Match",
                                style = MaterialTheme.typography.titleMedium,
                                color = OnSurface
                            )
                            Text(
                                text = "Optimal Fit",
                                style = MaterialTheme.typography.labelSmall,
                                color = SuccessGreen
                            )
                        }
                        LinearProgressIndicator(
                            progress = { 0.55f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = PrimaryBlack,
                            trackColor = BorderSubtle,
                        )
                        Text(
                            text = "Matched cab will be a spacious Sedan or 6-seater SUV (Ertiga / Innova).",
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondaryText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(
                text = "Next: Language Preference",
                onClick = onNext,
                trailingIcon = Icons.Default.ArrowForward
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LanguagePreferenceScreen(
    onBack: () -> Unit,
    onFindPool: () -> Unit
) {
    var selectedLanguages by remember { mutableStateOf(setOf("English", "Hindi")) }
    var quietRide by remember { mutableStateOf(true) }

    val languages = listOf("English", "Hindi", "Punjabi", "Bengali", "Tamil", "Telugu", "Kannada", "Marathi")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(
            title = "Ride Preferences",
            onBackClick = onBack,
            stepIndicator = "Step 5 of 5"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Comfort & Conversation",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Choose your preferred spoken languages and conversation mode.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryText
                    )
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text(
                            text = "Spoken Languages",
                            style = MaterialTheme.typography.titleMedium,
                            color = OnSurface
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            languages.take(3).forEach { lang ->
                                val isSelected = selectedLanguages.contains(lang)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) PrimaryBlack else SurfaceBeige)
                                        .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
                                        .clickable {
                                            selectedLanguages = if (isSelected) {
                                                selectedLanguages - lang
                                            } else {
                                                selectedLanguages + lang
                                            }
                                        }
                                        .padding(horizontal = 14.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = lang,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (isSelected) OnPrimary else OnSurface
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = BorderSubtle, thickness = 1.dp)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Quiet Ride Mode",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = OnSurface
                                )
                                Text(
                                    text = "Low-conversation ride for restful travel",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SecondaryText
                                )
                            }
                            Switch(
                                checked = quietRide,
                                onCheckedChange = { quietRide = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = OnPrimary,
                                    checkedTrackColor = PrimaryBlack
                                )
                            )
                        }
                    }
                }

                // Fare Estimate Card
                WayMateCard(backgroundColor = SurfaceCard) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Estimated Pool Fare",
                                style = MaterialTheme.typography.titleMedium,
                                color = OnSurface
                            )
                            Text(
                                text = "₹400 / seat",
                                style = MonoMedium.copy(fontWeight = FontWeight.Bold),
                                color = OnSurface
                            )
                        }
                        Text(
                            text = "Standard solo airport cab fare is ~₹1,200. You save 66% with WayMate.",
                            style = MaterialTheme.typography.bodySmall,
                            color = SuccessGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(
                text = "Find My Pool",
                onClick = onFindPool,
                leadingIcon = Icons.Default.Search
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

