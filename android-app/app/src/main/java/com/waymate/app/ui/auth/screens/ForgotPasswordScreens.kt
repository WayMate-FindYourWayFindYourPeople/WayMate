package com.waymate.app.ui.auth.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waymate.app.ui.components.AuthEmblemType
import com.waymate.app.ui.components.AuthHeaderEmblem
import com.waymate.app.ui.components.AuthScreenShell
import com.waymate.app.ui.components.OtpSegmentedInput
import com.waymate.app.ui.components.ResendOtpTimer
import com.waymate.app.ui.components.WayMatePrimaryButton
import com.waymate.app.ui.components.WayMateTextField
import com.waymate.app.ui.theme.CharcoalText
import com.waymate.app.ui.theme.ErrorRed
import com.waymate.app.ui.theme.MutedText
import com.waymate.app.ui.theme.WayMateTheme

@Composable
fun ForgotPasswordScreen(
    email: String,
    isLoading: Boolean,
    emailError: String?,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onSendOtpClick: () -> Unit
) {
    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = false,
        onBackClick = onBackClick
    ) {
        AuthHeaderEmblem(
            type = AuthEmblemType.ENVELOPE_QUESTION,
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.headlineMedium,
            color = CharcoalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your email address and\nwe'll send you an OTP to reset\nyour password.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

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
    }
}

@Composable
fun VerifyResetOtpScreen(
    otp: String,
    secondsRemaining: Int,
    isLoading: Boolean,
    otpError: String?,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onOtpChange: (String) -> Unit,
    onResendClick: () -> Unit,
    onVerifyClick: () -> Unit
) {
    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = false,
        onBackClick = onBackClick
    ) {
        AuthHeaderEmblem(
            type = AuthEmblemType.ENVELOPE_CHECK,
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Verify OTP",
            style = MaterialTheme.typography.headlineMedium,
            color = CharcoalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter the 6-digit code sent to\nyour email address.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

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
            onResendClick = onResendClick,
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(26.dp))

        WayMatePrimaryButton(
            text = "Verify",
            onClick = onVerifyClick,
            isLoading = isLoading,
            enabled = otp.length == 6,
            showArrow = true
        )
    }
}

@Composable
fun ChangePasswordScreen(
    newPassword: String,
    confirmPassword: String,
    isLoading: Boolean,
    newPasswordError: String?,
    confirmPasswordError: String?,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onUpdatePasswordClick: () -> Unit
) {
    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = false,
        onBackClick = onBackClick
    ) {
        AuthHeaderEmblem(
            type = AuthEmblemType.PADLOCK,
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Change Password",
            style = MaterialTheme.typography.headlineMedium,
            color = CharcoalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Set a new password for your\nWayMate account.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        WayMateTextField(
            value = newPassword,
            onValueChange = onNewPasswordChange,
            placeholder = "New Password",
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
            errorMessage = newPasswordError,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(12.dp))

        WayMateTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = "Confirm New Password",
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
            errorMessage = confirmPasswordError,
            imeAction = ImeAction.Done,
            onImeAction = onUpdatePasswordClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        WayMatePrimaryButton(
            text = "Update Password",
            onClick = onUpdatePasswordClick,
            isLoading = isLoading,
            showArrow = true
        )
    }
}

@Composable
fun PasswordUpdatedScreen(
    snackbarHostState: SnackbarHostState,
    onBackToLoginClick: () -> Unit
) {
    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = false,
        onBackClick = onBackToLoginClick
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        AuthHeaderEmblem(
            type = AuthEmblemType.SUCCESS_BADGE,
            modifier = Modifier.size(92.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Password Updated!",
            style = MaterialTheme.typography.headlineMedium,
            color = CharcoalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your password has been\nsuccessfully changed.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        WayMatePrimaryButton(
            text = "Back to Login",
            onClick = onBackToLoginClick,
            showArrow = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordPreview() {
    WayMateTheme {
        ForgotPasswordScreen(
            email = "",
            isLoading = false,
            emailError = null,
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onEmailChange = {},
            onSendOtpClick = {}
        )
    }
}
