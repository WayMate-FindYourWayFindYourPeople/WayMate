package com.waymate.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waymate.app.ui.theme.BorderWarm
import com.waymate.app.ui.theme.CharcoalText
import com.waymate.app.ui.theme.CreamBackground
import com.waymate.app.ui.theme.CreamSurface
import com.waymate.app.ui.theme.ErrorRed
import com.waymate.app.ui.theme.ForestGreen
import com.waymate.app.ui.theme.MutedText
import com.waymate.app.ui.theme.PlaceholderText
import com.waymate.app.ui.theme.SageOlive
import java.util.Locale

@Composable
fun AuthScreenShell(
    snackbarHostState: SnackbarHostState,
    showMountainHeader: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        containerColor = CreamBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.navigationBarsPadding().imePadding()
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamBackground)
                .padding(innerPadding)
        ) {
            if (showMountainHeader) {
                NatureHeaderBackground(
                    modifier = Modifier.align(Alignment.TopCenter),
                    height = 172.dp
                )
            }

            BotanicalFooterDecoration(
                modifier = Modifier.align(Alignment.BottomCenter),
                height = 92.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (onBackClick != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back",
                                tint = CharcoalText
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                if (showMountainHeader) {
                    Spacer(modifier = Modifier.height(52.dp))
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                content()

                Spacer(modifier = Modifier.height(76.dp))
            }
        }
    }
}

@Composable
fun WayMatePrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    showArrow: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = ForestGreen,
            contentColor = Color.White,
            disabledContainerColor = ForestGreen.copy(alpha = 0.55f),
            disabledContentColor = Color.White.copy(alpha = 0.85f)
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White,
                strokeWidth = 2.2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
            if (showArrow) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun WayMateOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = CircleShape,
        border = BorderStroke(1.dp, BorderWarm),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = CreamSurface,
            contentColor = CharcoalText
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(modifier = Modifier.width(10.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = CharcoalText
        )
    }
}

@Composable
fun WayMateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    readOnly: Boolean = false,
    errorMessage: String? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            singleLine = true,
            isError = errorMessage != null,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = PlaceholderText
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (errorMessage != null) ErrorRed else CharcoalText.copy(alpha = 0.78f),
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                when {
                    trailingContent != null -> trailingContent()
                    isPassword -> {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = MutedText,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            },
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction?.invoke() },
                onNext = { onImeAction?.invoke() },
                onSend = { onImeAction?.invoke() }
            ),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CreamSurface,
                unfocusedContainerColor = CreamSurface,
                disabledContainerColor = CreamSurface,
                errorContainerColor = CreamSurface,
                focusedBorderColor = ForestGreen,
                unfocusedBorderColor = BorderWarm,
                errorBorderColor = ErrorRed,
                focusedTextColor = CharcoalText,
                unfocusedTextColor = CharcoalText,
                cursorColor = ForestGreen
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = ErrorRed,
                modifier = Modifier.padding(start = 6.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun OtpSegmentedInput(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onComplete: (() -> Unit)? = null
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { focusRequester.requestFocus() },
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = otp,
            onValueChange = { raw ->
                val filtered = raw.filter { it.isDigit() }.take(6)
                onOtpChange(filtered)
                if (filtered.length == 6 && otp.length < 6) {
                    onComplete?.invoke()
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (otp.length == 6) onComplete?.invoke() }
            ),
            cursorBrush = SolidColor(Color.Transparent),
            modifier = Modifier
                .size(1.dp)
                .alpha(0.01f)
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(6) { index ->
                val char = otp.getOrNull(index)?.toString() ?: ""
                val isActiveCell = isFocused && (index == otp.length || (otp.length == 6 && index == 5))
                val borderColor = when {
                    isError -> ErrorRed
                    isActiveCell || char.isNotEmpty() -> SageOlive
                    else -> BorderWarm
                }
                val borderWidth = if (isActiveCell) 1.8.dp else 1.1.dp

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .background(CreamSurface, RoundedCornerShape(12.dp))
                        .border(borderWidth, borderColor, RoundedCornerShape(12.dp))
                        .clickable { focusRequester.requestFocus() },
                    contentAlignment = Alignment.Center
                ) {
                    if (char.isNotEmpty()) {
                        Text(
                            text = char,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalText,
                            textAlign = TextAlign.Center
                        )
                    } else if (isActiveCell) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(22.dp)
                                .background(ForestGreen, RoundedCornerShape(1.dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResendOtpTimer(
    secondsRemaining: Int,
    onResendClick: () -> Unit,
    isLoading: Boolean = false
) {
    if (secondsRemaining > 0) {
        val mins = secondsRemaining / 60
        val secs = secondsRemaining % 60
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Resend OTP in ",
                style = MaterialTheme.typography.bodyMedium,
                color = MutedText
            )
            Text(
                text = String.format(Locale.US, "%02d:%02d", mins, secs),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = CharcoalText
            )
        }
    } else {
        Text(
            text = if (isLoading) "Sending OTP..." else "Resend OTP",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = ForestGreen,
            modifier = Modifier
                .clickable(enabled = !isLoading, onClick = onResendClick)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun OrDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = BorderWarm
        )
        Text(
            text = "OR",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = MutedText,
            modifier = Modifier.padding(horizontal = 14.dp)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = BorderWarm
        )
    }
}
