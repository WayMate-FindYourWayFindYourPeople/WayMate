package com.waymate.app.ui.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waymate.app.ui.components.WayMateOutlinedButton
import com.waymate.app.ui.components.WayMatePrimaryButton
import com.waymate.app.ui.components.WelcomeMountainBackground
import com.waymate.app.ui.theme.CharcoalText
import com.waymate.app.ui.theme.MutedText
import com.waymate.app.ui.theme.WayMateTheme
import com.waymate.app.ui.theme.WayMateWordmarkStyle

@Composable
fun WelcomeScreen(
    onCreateAccountClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        WelcomeMountainBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(0.45f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "WayMate",
                    style = WayMateWordmarkStyle,
                    color = CharcoalText,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Map your journeys,\nmemories and the\nplaces that matter.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MutedText,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(0.35f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WayMatePrimaryButton(
                    text = "Create Account",
                    onClick = onCreateAccountClick,
                    showArrow = true
                )
                WayMateOutlinedButton(
                    text = "Log In",
                    onClick = onLoginClick
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomeScreenPreview() {
    WayMateTheme {
        WelcomeScreen(
            onCreateAccountClick = {},
            onLoginClick = {}
        )
    }
}
