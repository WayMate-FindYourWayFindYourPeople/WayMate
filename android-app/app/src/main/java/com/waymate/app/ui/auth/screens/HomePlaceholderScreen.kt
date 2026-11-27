package com.waymate.app.ui.auth.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.waymate.app.ui.components.AuthEmblemType
import com.waymate.app.ui.components.AuthHeaderEmblem
import com.waymate.app.ui.components.AuthScreenShell
import com.waymate.app.ui.components.WayMateOutlinedButton
import com.waymate.app.ui.components.WayMatePrimaryButton
import com.waymate.app.ui.theme.BorderWarm
import com.waymate.app.ui.theme.CharcoalText
import com.waymate.app.ui.theme.CreamSurface
import com.waymate.app.ui.theme.ErrorRed
import com.waymate.app.ui.theme.MutedText
import com.waymate.app.ui.theme.WayMateWordmarkStyle

@Composable
fun HomePlaceholderScreen(
    userEmail: String?,
    userId: String?,
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    onRefreshSessionClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = CreamSurface,
            title = {
                Text(
                    text = "Delete Account?",
                    style = MaterialTheme.typography.titleMedium,
                    color = CharcoalText
                )
            },
            text = {
                Text(
                    text = "This permanently deactivates your WayMate account and revokes all active sessions.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedText
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteAccountClick()
                    }
                ) {
                    Text("Delete", color = ErrorRed, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = CharcoalText)
                }
            }
        )
    }

    AuthScreenShell(
        snackbarHostState = snackbarHostState,
        showMountainHeader = true,
        onBackClick = null
    ) {
        AuthHeaderEmblem(
            type = AuthEmblemType.SUCCESS_BADGE,
            modifier = Modifier.size(68.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "WayMate",
            style = WayMateWordmarkStyle,
            color = CharcoalText
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "You're signed in and ready to explore.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = CreamSurface,
            border = BorderStroke(1.dp, BorderWarm),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (!userEmail.isNullOrBlank()) {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.bodySmall,
                        color = MutedText
                    )
                    Text(
                        text = userEmail,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = CharcoalText
                    )
                }
                Text(
                    text = "Authenticated User ID (/api/auth/me)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText
                )
                Text(
                    text = userId ?: "Tap Verify Session to fetch from backend",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CharcoalText
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        WayMatePrimaryButton(
            text = "Verify Session",
            onClick = onRefreshSessionClick,
            isLoading = isLoading,
            showArrow = false
        )

        Spacer(modifier = Modifier.height(12.dp))

        WayMateOutlinedButton(
            text = "Log Out",
            onClick = onLogoutClick,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { showDeleteDialog = true },
            enabled = !isLoading
        ) {
            Text(
                text = "Delete Account",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = ErrorRed
            )
        }
    }
}
