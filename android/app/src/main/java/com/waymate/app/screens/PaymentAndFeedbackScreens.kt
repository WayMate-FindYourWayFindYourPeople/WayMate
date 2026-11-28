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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waymate.app.components.*
import com.waymate.app.theme.*

@Composable
fun PaymentRazorpayScreen(
    onBack: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf("UPI - Google Pay") }
    val paymentOptions = listOf(
        "UPI - Google Pay",
        "UPI - PhonePe",
        "UPI - Paytm",
        "Credit / Debit Card",
        "Net Banking"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
    ) {
        WayMateTopBar(title = "Payment", onBackClick = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Razorpay Secured Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Secure Checkout",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Powered by Razorpay",
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondaryText
                        )
                    }
                    WayMateBadge(text = "256-bit Encrypted", isSuccess = true)
                }

                // Fare Breakdown Card
                WayMateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Fare Breakdown", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Total Trip Fare", color = SecondaryText)
                            Text(text = "₹1,050", style = MonoSmall)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Airport Toll & Parking", color = SecondaryText)
                            Text(text = "₹150", style = MonoSmall)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Pool Savings Discount", color = SuccessGreen)
                            Text(text = "- ₹800", style = MonoSmall, color = SuccessGreen)
                        }

                        HorizontalDivider(color = BorderSubtle, thickness = 1.dp)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Amount to Pay", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "₹400", style = MonoMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                        }
                    }
                }

                // Payment Options
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "Select Payment Method", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

                    paymentOptions.forEach { method ->
                        val isSelected = method == selectedMethod
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) SurfaceSecondary else SurfaceCard)
                                .border(1.dp, if (isSelected) PrimaryBlack else BorderSubtle, RoundedCornerShape(12.dp))
                                .clickable { selectedMethod = method }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (method.contains("UPI")) Icons.Default.QrCode else Icons.Default.CreditCard,
                                    contentDescription = null,
                                    tint = PrimaryBlack
                                )
                                Text(text = method, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            }
                            if (isSelected) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryBlack)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WayMatePrimaryButton(
                text = "Pay ₹400 Now",
                onClick = onPaymentSuccess,
                leadingIcon = Icons.Default.Lock
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RatingFeedbackScreen(
    onSubmit: () -> Unit
) {
    var rating by remember { mutableIntStateOf(5) }
    var selectedTip by remember { mutableStateOf("₹50") }
    var selectedTags by remember { mutableStateOf(setOf("Punctual", "Clean Cab", "Safe Driving")) }

    val feedbackTags = listOf("Punctual", "Clean Cab", "Safe Driving", "Courteous", "Quiet Ride")
    val tipOptions = listOf("No Tip", "₹20", "₹50", "₹100")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBeige)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
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
                Text(
                    text = "Rate Ramesh Kumar",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "How was your airport transfer to Noida?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryText
                )

                // 5-Star Rating Selector
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    (1..5).forEach { star ->
                        IconButton(
                            onClick = { rating = star },
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = if (star <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "$star Stars",
                                tint = if (star <= rating) PrimaryBlack else SecondaryText,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }

            // Quick Feedback Tags
            WayMateCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "What did you like?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        feedbackTags.take(3).forEach { tag ->
                            val isSelected = selectedTags.contains(tag)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) PrimaryBlack else SurfaceBeige)
                                    .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
                                    .clickable {
                                        selectedTags = if (isSelected) selectedTags - tag else selectedTags + tag
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isSelected) OnPrimary else OnSurface
                                )
                            }
                        }
                    }
                }
            }

            // Driver Tip Option
            WayMateCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "Add a Tip for Ramesh", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tipOptions.forEach { tip ->
                            val isSelected = tip == selectedTip
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) PrimaryBlack else SurfaceBeige)
                                    .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
                                    .clickable { selectedTip = tip }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tip,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isSelected) OnPrimary else OnSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        WayMatePrimaryButton(
            text = "Submit & Return to Dashboard",
            onClick = onSubmit,
            trailingIcon = Icons.Default.Check
        )
    }
}

