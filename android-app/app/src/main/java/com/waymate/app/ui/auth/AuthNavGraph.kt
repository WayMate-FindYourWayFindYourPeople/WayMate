package com.waymate.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.waymate.app.ui.auth.screens.AuthEntryScreen
import com.waymate.app.ui.auth.screens.ChangePasswordScreen
import com.waymate.app.ui.auth.screens.CreateAccountScreen
import com.waymate.app.ui.auth.screens.ForgotPasswordScreen
import com.waymate.app.ui.auth.screens.HomePlaceholderScreen
import com.waymate.app.ui.auth.screens.LoginScreen
import com.waymate.app.ui.auth.screens.PasswordUpdatedScreen
import com.waymate.app.ui.auth.screens.VerifyEmailScreen
import com.waymate.app.ui.auth.screens.VerifyResetOtpScreen
import com.waymate.app.ui.auth.screens.WelcomeScreen
import com.waymate.app.ui.theme.CreamBackground
import com.waymate.app.ui.theme.ForestGreen
import kotlinx.coroutines.launch

object AuthRoutes {
    const val WELCOME = "welcome"
    const val AUTH_ENTRY = "auth_entry"
    const val VERIFY_EMAIL = "verify_email"
    const val CREATE_ACCOUNT = "create_account"
    const val LOGIN = "login"
    const val FORGOT_PASSWORD = "forgot_password"
    const val VERIFY_RESET_OTP = "verify_reset_otp"
    const val CHANGE_PASSWORD = "change_password"
    const val PASSWORD_UPDATED = "password_updated"
    const val HOME = "home"
}

@Composable
fun WayMateAuthNavHost(
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is AuthEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is AuthEvent.Navigate -> {
                    navController.navigate(event.route) {
                        launchSingleTop = true
                        if (event.popUpToRoute != null) {
                            popUpTo(event.popUpToRoute) {
                                inclusive = event.inclusive
                            }
                        }
                    }
                }
            }
        }
    }

    val triggerGoogleSignIn: () -> Unit = {
        coroutineScope.launch {
            when (val result = GoogleSignInHelper.signIn(context)) {
                is GoogleSignInResult.Success -> {
                    viewModel.authenticateWithGoogleIdToken(result.idToken)
                }
                is GoogleSignInResult.Cancelled -> Unit
                is GoogleSignInResult.Error -> {
                    viewModel.showMessage(result.message)
                }
            }
        }
    }

    if (uiState.isRestoringSession) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamBackground),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = ForestGreen)
        }
        return
    }

    val startDestination = if (uiState.isAuthenticated) AuthRoutes.HOME else AuthRoutes.WELCOME

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AuthRoutes.WELCOME) {
            WelcomeScreen(
                onCreateAccountClick = { navController.navigate(AuthRoutes.AUTH_ENTRY) },
                onLoginClick = { navController.navigate(AuthRoutes.LOGIN) }
            )
        }

        composable(AuthRoutes.AUTH_ENTRY) {
            AuthEntryScreen(
                isLoading = uiState.isLoading,
                snackbarHostState = snackbarHostState,
                onBackClick = { navController.popBackStack() },
                onContinueWithGoogleClick = triggerGoogleSignIn,
                onCreateWithEmailClick = { navController.navigate(AuthRoutes.VERIFY_EMAIL) },
                onLoginClick = { navController.navigate(AuthRoutes.LOGIN) }
            )
        }

        composable(AuthRoutes.VERIFY_EMAIL) {
            VerifyEmailScreen(
                email = uiState.signUpEmail,
                emailSubmitted = uiState.signUpEmailSubmitted,
                otp = uiState.signUpOtp,
                secondsRemaining = uiState.signUpOtpSecondsLeft,
                isLoading = uiState.isLoading,
                emailError = uiState.fieldErrors["signUpEmail"],
                otpError = uiState.fieldErrors["signUpOtp"],
                snackbarHostState = snackbarHostState,
                onBackClick = { navController.popBackStack() },
                onEmailChange = viewModel::onSignUpEmailChange,
                onSendOtpClick = viewModel::requestSignUpOtp,
                onChangeEmailClick = viewModel::onChangeSignUpEmail,
                onOtpChange = viewModel::onSignUpOtpChange,
                onVerifyClick = viewModel::verifySignUpOtp
            )
        }

        composable(AuthRoutes.CREATE_ACCOUNT) {
            CreateAccountScreen(
                verifiedEmail = uiState.signUpEmail,
                fullName = uiState.signUpFullName,
                password = uiState.signUpPassword,
                confirmPassword = uiState.signUpConfirmPassword,
                isLoading = uiState.isLoading,
                fullNameError = uiState.fieldErrors["fullName"],
                passwordError = uiState.fieldErrors["signUpPassword"],
                confirmPasswordError = uiState.fieldErrors["signUpConfirmPassword"],
                snackbarHostState = snackbarHostState,
                onBackClick = { navController.popBackStack() },
                onFullNameChange = viewModel::onSignUpFullNameChange,
                onPasswordChange = viewModel::onSignUpPasswordChange,
                onConfirmPasswordChange = viewModel::onSignUpConfirmPasswordChange,
                onCreateAccountClick = viewModel::completeRegistration
            )
        }

        composable(AuthRoutes.LOGIN) {
            LoginScreen(
                email = uiState.loginEmail,
                password = uiState.loginPassword,
                rememberMe = uiState.rememberMe,
                isLoading = uiState.isLoading,
                emailError = uiState.fieldErrors["loginEmail"],
                passwordError = uiState.fieldErrors["loginPassword"],
                snackbarHostState = snackbarHostState,
                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(AuthRoutes.WELCOME)
                    }
                },
                onEmailChange = viewModel::onLoginEmailChange,
                onPasswordChange = viewModel::onLoginPasswordChange,
                onRememberMeChange = viewModel::onRememberMeChange,
                onForgotPasswordClick = {
                    if (uiState.resetEmail.isBlank() && uiState.loginEmail.isNotBlank()) {
                        viewModel.onResetEmailChange(uiState.loginEmail)
                    }
                    navController.navigate(AuthRoutes.FORGOT_PASSWORD)
                },
                onLoginClick = viewModel::login,
                onGoogleSignInClick = triggerGoogleSignIn,
                onCreateAccountClick = { navController.navigate(AuthRoutes.AUTH_ENTRY) }
            )
        }

        composable(AuthRoutes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                email = uiState.resetEmail,
                isLoading = uiState.isLoading,
                emailError = uiState.fieldErrors["resetEmail"],
                snackbarHostState = snackbarHostState,
                onBackClick = { navController.popBackStack() },
                onEmailChange = viewModel::onResetEmailChange,
                onSendOtpClick = { viewModel.sendForgotPasswordOtp(navigateToVerify = true) }
            )
        }

        composable(AuthRoutes.VERIFY_RESET_OTP) {
            VerifyResetOtpScreen(
                otp = uiState.resetOtp,
                secondsRemaining = uiState.resetOtpSecondsLeft,
                isLoading = uiState.isLoading,
                otpError = uiState.fieldErrors["resetOtp"],
                snackbarHostState = snackbarHostState,
                onBackClick = { navController.popBackStack() },
                onOtpChange = viewModel::onResetOtpChange,
                onResendClick = { viewModel.sendForgotPasswordOtp(navigateToVerify = false) },
                onVerifyClick = viewModel::verifyResetOtp
            )
        }

        composable(AuthRoutes.CHANGE_PASSWORD) {
            ChangePasswordScreen(
                newPassword = uiState.resetNewPassword,
                confirmPassword = uiState.resetConfirmPassword,
                isLoading = uiState.isLoading,
                newPasswordError = uiState.fieldErrors["newPassword"],
                confirmPasswordError = uiState.fieldErrors["confirmNewPassword"],
                snackbarHostState = snackbarHostState,
                onBackClick = { navController.popBackStack() },
                onNewPasswordChange = viewModel::onResetNewPasswordChange,
                onConfirmPasswordChange = viewModel::onResetConfirmPasswordChange,
                onUpdatePasswordClick = viewModel::submitNewPassword
            )
        }

        composable(AuthRoutes.PASSWORD_UPDATED) {
            PasswordUpdatedScreen(
                snackbarHostState = snackbarHostState,
                onBackToLoginClick = {
                    navController.navigate(AuthRoutes.LOGIN) {
                        popUpTo(AuthRoutes.WELCOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AuthRoutes.HOME) {
            HomePlaceholderScreen(
                userEmail = uiState.currentUserEmail,
                userId = uiState.currentUserId,
                isLoading = uiState.isLoading,
                snackbarHostState = snackbarHostState,
                onRefreshSessionClick = { viewModel.fetchCurrentUserId(silent = false) },
                onLogoutClick = viewModel::logout,
                onDeleteAccountClick = viewModel::deleteAccount
            )
        }
    }
}
