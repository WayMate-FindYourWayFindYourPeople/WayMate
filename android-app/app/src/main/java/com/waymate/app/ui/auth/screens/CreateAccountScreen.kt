package com.waymate.app.ui.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waymate.app.ui.components.AuthEmblemType
import com.waymate.app.ui.components.AuthHeaderEmblem
import com.waymate.app.ui.components.AuthScreenShell
import com.waymate.app.ui.components.WayMatePrimaryButton
import com.waymate.app.ui.components.WayMateTextField
import com.waymate.app.ui.theme.CharcoalText
import com.waymate.app.ui.theme.MutedText
import com.waymate.app.ui.theme.VerifiedGreen
import com.waymate.app.ui.theme.WayMateTheme

@Composable
fun CreateAccountScreen(
    verifiedEmail: String,
    fullName: String,
    password: String,
    confirmPassword: String,
    isLoading: Boolean,
    fullNameError: String?,
    passwordError: String?,
    confirmPasswordError: String?,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onFullNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onCreateAccountClick: () -> Unit
) {
    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = true,
        onBackClick = onBackClick
    ) {
        AuthHeaderEmblem(
            type = AuthEmblemType.ENVELOPE,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            color = CharcoalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Your email is verified. Complete\nyour profile to get started.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(22.dp))

        WayMateTextField(
            value = verifiedEmail,
            onValueChange = {},
            placeholder = "Email",
            leadingIcon = Icons.Outlined.MailOutline,
            readOnly = true,
            trailingContent = {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(VerifiedGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Email verified",
                        tint = Color.White,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        WayMateTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            placeholder = "Full Name",
            leadingIcon = Icons.Outlined.PersonOutline,
            errorMessage = fullNameError,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(12.dp))

        WayMateTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "Password",
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
            errorMessage = passwordError,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(12.dp))

        WayMateTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = "Confirm Password",
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
            errorMessage = confirmPasswordError,
            imeAction = ImeAction.Done,
            onImeAction = onCreateAccountClick
        )

        Spacer(modifier = Modifier.height(22.dp))

        WayMatePrimaryButton(
            text = "Create Account",
            onClick = onCreateAccountClick,
            isLoading = isLoading,
            showArrow = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateAccountScreenPreview() {
    WayMateTheme {
        CreateAccountScreen(
            verifiedEmail = "name@gmail.com",
            fullName = "",
            password = "",
            confirmPassword = "",
            isLoading = false,
            fullNameError = null,
            passwordError = null,
            confirmPasswordError = null,
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onFullNameChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onCreateAccountClick = {}
        )
    }
}
