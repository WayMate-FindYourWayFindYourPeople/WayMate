package com.waymate.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val WayMateColorScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = Color.White,
    primaryContainer = VerifiedSoftBg,
    onPrimaryContainer = ForestGreenDark,
    secondary = SageOlive,
    onSecondary = Color.White,
    secondaryContainer = CreamSurface,
    onSecondaryContainer = CharcoalText,
    tertiary = SageSoft,
    background = CreamBackground,
    onBackground = CharcoalText,
    surface = CreamSurface,
    onSurface = CharcoalText,
    surfaceVariant = CreamBackground,
    onSurfaceVariant = MutedText,
    outline = BorderWarm,
    outlineVariant = BorderWarm,
    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorSoftBg,
    onErrorContainer = ErrorRed
)

@Composable
fun WayMateTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = WayMateColorScheme,
        typography = Typography,
        content = content
    )
}
