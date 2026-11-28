package com.waymate.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waymate.app.components.*
import com.waymate.app.theme.*

@Composable
fun SplashScreen(
    onGetStarted: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
            .padding(horizontal = 20.dp, vertical = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Airports Tag
            Text(
                text = "DEL · BOM · BLR · HYD",
                style = MonoSmall,
                color = SecondaryText,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Center Branding & Hero
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(PrimaryBlack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FlightLand,
                        contentDescription = null,
                        tint = OnPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "WayMate",
                        style = MaterialTheme.typography.displayLarge,
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Airport rides, shared smarter.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryText
                    )
                }

                // Visual Feature Pill Card
                WayMateCard(
                    backgroundColor = SurfaceCard,
                    borderColor = BorderSubtle,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WayMateBadge(text = "Save up to 66%", isSuccess = true)
                            WayMateBadge(text = "Flight-Synced", isSuccess = true)
                        }
                        Text(
                            text = "Connect with passengers landing on your flight or terminal heading in your direction.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SecondaryText,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Bottom CTA
            WayMatePrimaryButton(
                text = "Get Started",
                onClick = onGetStarted,
                trailingIcon = Icons.Default.ArrowForward
            )
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinue: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }
    val steps = listOf(
        Triple(
            "Sync Your Flight",
            "Enter your arrival flight details. WayMate automatically monitors landing times and terminal gates.",
            Icons.Default.Flight
        ),
        Triple(
            "Smart Route Pooling",
            "Match with verified co-passengers travelling along your drop-off corridor and split fares up to 66%.",
            Icons.Default.Group
        ),
        Triple(
            "Guaranteed Boot Space",
            "Every pool algorithmically accounts for cabin and check-in luggage so your ride is always comfortable.",
            Icons.Default.Luggage
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
            .padding(horizontal = 20.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "WayMate",
                style = MaterialTheme.typography.headlineMedium,
                color = OnSurface,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = onContinue) {
                Text(text = "Skip", style = MaterialTheme.typography.labelLarge, color = SecondaryText)
            }
        }

        // Feature Carousel Item
        val (title, description, icon) = steps[currentStep]
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SurfaceSecondary)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PrimaryBlack,
                    modifier = Modifier.size(46.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryText,
                    textAlign = TextAlign.Center
                )
            }

            // Step Dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                steps.indices.forEach { index ->
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width(if (index == currentStep) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (index == currentStep) PrimaryBlack else BorderSubtle)
                    )
                }
            }
        }

        // Bottom CTA
        WayMatePrimaryButton(
            text = if (currentStep < steps.size - 1) "Next" else "Continue to Sign In",
            onClick = {
                if (currentStep < steps.size - 1) {
                    currentStep++
                } else {
                    onContinue()
                }
            },
            trailingIcon = Icons.Default.ArrowForward
        )
    }
}

@Composable
fun LoginOtpScreen(
    onLoginSuccess: () -> Unit
) {
    var isOtpStep by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("9876543210") }
    var otpDigits by remember { mutableStateOf(listOf("4", "8", "2", "9", "1", "6")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
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
                    style = MaterialTheme.typography.headlineMedium,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            // Mode Selector Pill
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SurfaceSecondary)
                    .border(1.dp, BorderSubtle, CircleShape)
                    .padding(3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (!isOtpStep) PrimaryBlack else Color.Transparent)
                        .clickable { isOtpStep = false }
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Mobile",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (!isOtpStep) OnPrimary else SecondaryText
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (isOtpStep) PrimaryBlack else Color.Transparent)
                        .clickable { isOtpStep = true }
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "OTP",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isOtpStep) OnPrimary else SecondaryText
                    )
                }
            }
        }

        if (!isOtpStep) {
            // Mobile Number Step
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sign in with your Indian mobile number to plan or join an airport pool.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryText
                    )
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        WayMateTextField(
                            value = phoneNumber,
                            onValueChange = { if (it.length <= 10) phoneNumber = it },
                            label = "Mobile number",
                            prefixText = "+91",
                            placeholder = "98765 43210",
                            isMonospace = true
                        )
                        Text(
                            text = "We will send a 6-digit verification code via SMS",
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondaryText
                        )
                        WayMatePrimaryButton(
                            text = "Continue with OTP",
                            onClick = { isOtpStep = true }
                        )
                    }
                }
            }
        } else {
            // OTP Verification Step
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    TextButton(
                        onClick = { isOtpStep = false },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = SecondaryText,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Change number",
                            style = MaterialTheme.typography.labelMedium,
                            color = SecondaryText
                        )
                    }
                    Text(
                        text = "Verify OTP",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Enter the 6-digit code sent to +91 $phoneNumber",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryText
                    )
                }

                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            otpDigits.forEachIndexed { index, digit ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SurfaceBeige)
                                        .border(1.dp, PrimaryBlack, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = digit,
                                        style = MonoMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                                        color = OnSurface
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Resend available in 00:24",
                                style = MaterialTheme.typography.bodySmall,
                                color = SecondaryText
                            )
                            Text(
                                text = "Resend Code",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryBlack,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        WayMatePrimaryButton(
                            text = "Verify & Continue",
                            onClick = onLoginSuccess
                        )
                    }
                }
            }
        }

        // Footer Safety Note
        Text(
            text = "By continuing, you agree to WayMate's Terms of Service and Privacy Policy.",
            style = MaterialTheme.typography.bodySmall,
            color = SecondaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

