package com.waymate.app.ui.auth.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waymate.app.ui.components.AuthEmblemType
import com.waymate.app.ui.components.AuthHeaderEmblem
import com.waymate.app.ui.components.AuthScreenShell
import com.waymate.app.ui.components.OtpSegmentedInput
import com.waymate.app.ui.components.ResendOtpTimer
import com.waymate.app.ui.components.WayMatePrimaryButton
import com.waymate.app.ui.components.WayMateTextField
import com.waymate.app.ui.theme.BorderWarm
import com.waymate.app.ui.theme.CharcoalText
import com.waymate.app.ui.theme.CreamSurface
import com.waymate.app.ui.theme.ErrorRed
import com.waymate.app.ui.theme.ForestGreen
import com.waymate.app.ui.theme.MutedText
import com.waymate.app.ui.theme.VerifiedGreen
import com.waymate.app.ui.theme.WayMateTheme

@Composable
fun VerifyEmailScreen(
    email: String,
    emailSubmitted: Boolean,
    otp: String,
    secondsRemaining: Int,
    isLoading: Boolean,
    emailError: String?,
    otpError: String?,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onSendOtpClick: () -> Unit,
    onChangeEmailClick: () -> Unit,
    onOtpChange: (String) -> Unit,
    onVerifyClick: () -> Unit
) {
    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = false,
        onBackClick = onBackClick
    ) {
        AuthHeaderEmblem(
            type = AuthEmblemType.ENVELOPE,
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Verify Your Email",
            style = MaterialTheme.typography.headlineMedium,
            color = CharcoalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We'll send a 6-digit OTP to your\nemail to continue.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(26.dp))

        if (!emailSubmitted) {
            WayMateTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = "Email Address",
                leadingIcon = Icons.Outlined.MailOutline,
                errorMessage = emailError,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Send,
                onImeAction = onSendOtpClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            WayMatePrimaryButton(
                text = "Send OTP",
                onClick = onSendOtpClick,
                isLoading = isLoading,
                showArrow = true
            )
        } else {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CreamSurface,
                border = BorderStroke(1.dp, BorderWarm),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MailOutline,
                            contentDescription = null,
                            tint = CharcoalText,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CharcoalText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Change",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = VerifiedGreen,
                        modifier = Modifier
                            .clickable(enabled = !isLoading, onClick = onChangeEmailClick)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            OtpSegmentedInput(
                otp = otp,
                onOtpChange = onOtpChange,
                isError = otpError != null,
                onComplete = { if (!isLoading) onVerifyClick() }
            )

            if (otpError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = otpError,
                    style = MaterialTheme.typography.bodySmall,
                    color = ErrorRed,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            ResendOtpTimer(
                secondsRemaining = secondsRemaining,
                onResendClick = onSendOtpClick,
                isLoading = isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            WayMatePrimaryButton(
                text = "Verify",
                onClick = onVerifyClick,
                isLoading = isLoading,
                enabled = otp.length == 6,
                showArrow = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerifyEmailScreenPreview() {
    WayMateTheme {
        VerifyEmailScreen(
            email = "name@gmail.com",
            emailSubmitted = true,
            otp = "4",
            secondsRemaining = 58,
            isLoading = false,
            emailError = null,
            otpError = null,
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onEmailChange = {},
            onSendOtpClick = {},
            onChangeEmailClick = {},
            onOtpChange = {},
            onVerifyClick = {}
        )
    }
}
