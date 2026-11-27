package com.waymate.app.ui.auth.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waymate.app.ui.components.AuthScreenShell
import com.waymate.app.ui.components.GoogleGIcon
import com.waymate.app.ui.components.OrDivider
import com.waymate.app.ui.components.WayMateOutlinedButton
import com.waymate.app.ui.components.WayMatePrimaryButton
import com.waymate.app.ui.components.WayMateTextField
import com.waymate.app.ui.theme.BorderWarm
import com.waymate.app.ui.theme.CharcoalText
import com.waymate.app.ui.theme.ForestGreen
import com.waymate.app.ui.theme.MutedText
import com.waymate.app.ui.theme.WayMateTheme

@Composable
fun LoginScreen(
    email: String,
    password: String,
    rememberMe: Boolean,
    isLoading: Boolean,
    emailError: String?,
    passwordError: String?,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = true,
        onBackClick = onBackClick
    ) {
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineMedium,
            color = CharcoalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Sign in to continue your\njourney with WayMate",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(22.dp))

        WayMateTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Email Address",
            leadingIcon = Icons.Outlined.MailOutline,
            errorMessage = emailError,
            keyboardType = KeyboardType.Email,
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
            imeAction = ImeAction.Done,
            onImeAction = onLoginClick
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(enabled = !isLoading) { onRememberMeChange(!rememberMe) }
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = onRememberMeChange,
                    enabled = !isLoading,
                    colors = CheckboxDefaults.colors(
                        checkedColor = ForestGreen,
                        uncheckedColor = BorderWarm,
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = "Remember Me",
                    style = MaterialTheme.typography.bodySmall,
                    color = CharcoalText
                )
            }

            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = ForestGreen,
                modifier = Modifier
                    .clickable(enabled = !isLoading, onClick = onForgotPasswordClick)
                    .padding(vertical = 6.dp, horizontal = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        WayMatePrimaryButton(
            text = "Log In",
            onClick = onLoginClick,
            isLoading = isLoading,
            showArrow = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OrDivider()

        Spacer(modifier = Modifier.height(16.dp))

        WayMateOutlinedButton(
            text = "Continue with Google",
            onClick = onGoogleSignInClick,
            enabled = !isLoading,
            leadingIcon = { GoogleGIcon(modifier = Modifier.size(20.dp)) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodySmall,
                color = MutedText
            )
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = ForestGreen,
                modifier = Modifier
                    .clickable(enabled = !isLoading, onClick = onCreateAccountClick)
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    WayMateTheme {
        LoginScreen(
            email = "",
            password = "",
            rememberMe = false,
            isLoading = false,
            emailError = null,
            passwordError = null,
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onEmailChange = {},
            onPasswordChange = {},
            onRememberMeChange = {},
            onForgotPasswordClick = {},
            onLoginClick = {},
            onGoogleSignInClick = {},
            onCreateAccountClick = {}
        )
    }
}
