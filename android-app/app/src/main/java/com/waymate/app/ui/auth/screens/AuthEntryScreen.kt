package com.waymate.app.ui.auth.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waymate.app.ui.components.AuthScreenShell
import com.waymate.app.ui.components.GoogleGIcon
import com.waymate.app.ui.components.OrDivider
import com.waymate.app.ui.components.WayMateOutlinedButton
import com.waymate.app.ui.components.WayMatePrimaryButton
import com.waymate.app.ui.theme.CharcoalText
import com.waymate.app.ui.theme.ForestGreen
import com.waymate.app.ui.theme.MutedText
import com.waymate.app.ui.theme.WayMateTheme

@Composable
fun AuthEntryScreen(
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onContinueWithGoogleClick: () -> Unit,
    onCreateWithEmailClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = true,
        onBackClick = onBackClick
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        GoogleGIcon(modifier = Modifier.size(44.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Continue with Google",
            style = MaterialTheme.typography.headlineMedium,
            color = CharcoalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Quick and secure access\nto your WayMate account",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        WayMateOutlinedButton(
            text = if (isLoading) "Connecting..." else "Continue with Google",
            onClick = onContinueWithGoogleClick,
            enabled = !isLoading,
            leadingIcon = { GoogleGIcon(modifier = Modifier.size(20.dp)) }
        )

        Spacer(modifier = Modifier.height(18.dp))

        OrDivider()

        Spacer(modifier = Modifier.height(18.dp))

        WayMatePrimaryButton(
            text = "Create Account with Email",
            onClick = onCreateWithEmailClick,
            enabled = !isLoading,
            showArrow = false
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MutedText
            )
            Text(
                text = "Log In",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = ForestGreen,
                modifier = Modifier
                    .clickable(enabled = !isLoading, onClick = onLoginClick)
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthEntryScreenPreview() {
    WayMateTheme {
        AuthEntryScreen(
            isLoading = false,
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onContinueWithGoogleClick = {},
            onCreateWithEmailClick = {},
            onLoginClick = {}
        )
    }
}
