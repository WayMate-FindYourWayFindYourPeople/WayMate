package com.waymate.app.ui.auth

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.waymate.app.data.local.TokenManager
import com.waymate.app.data.remote.NetworkModule
import com.waymate.app.data.repository.AuthRepository
import com.waymate.app.data.repository.AuthResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val isRestoringSession: Boolean = true,
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val currentUserId: String? = null,
    val currentUserEmail: String? = null,

    // Login
    val loginEmail: String = "",
    val loginPassword: String = "",
    val rememberMe: Boolean = true,

    // Sign-up OTP + Profile
    val signUpEmail: String = "",
    val signUpEmailSubmitted: Boolean = false,
    val signUpOtp: String = "",
    val signUpOtpSecondsLeft: Int = 0,
    val signUpFullName: String = "",
    val signUpPassword: String = "",
    val signUpConfirmPassword: String = "",

    // Forgot Password flow
    val resetEmail: String = "",
    val resetOtp: String = "",
    val resetOtpSecondsLeft: Int = 0,
    val resetNewPassword: String = "",
    val resetConfirmPassword: String = "",

    // Field-level validation errors
    val fieldErrors: Map<String, String> = emptyMap()
)

sealed class AuthEvent {
    data class Navigate(val route: String, val popUpToRoute: String? = null, val inclusive: Boolean = false) : AuthEvent()
    data class ShowMessage(val message: String) : AuthEvent()
}

class AuthViewModel @JvmOverloads constructor(
    application: Application,
    private val repository: AuthRepository = createDefaultRepository(application)
) : AndroidViewModel(application) {

    companion object {
        private const val OTP_COUNTDOWN_SECONDS = 60

        private fun createDefaultRepository(application: Application): AuthRepository {
            val tokenManager = TokenManager(application.applicationContext)
            val api = NetworkModule.createAuthApiService(tokenManager)
            return AuthRepository(api, tokenManager)
        }
    }

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AuthEvent>(extraBufferCapacity = 8)
    val events: SharedFlow<AuthEvent> = _events.asSharedFlow()

    private var signUpTimerJob: Job? = null
    private var resetTimerJob: Job? = null

    init {
        restoreSessionOnStart()
        observeSessionChanges()
    }

    private fun restoreSessionOnStart() {
        viewModelScope.launch {
            val rememberedEmail = repository.rememberedEmailFlow.first()
            val rememberMe = repository.rememberMeFlow.first()
            val restored = repository.restoreSession()
            _uiState.update {
                it.copy(
                    isRestoringSession = false,
                    isAuthenticated = restored != null,
                    currentUserEmail = restored?.email ?: rememberedEmail.ifBlank { null },
                    loginEmail = rememberedEmail,
                    rememberMe = rememberMe
                )
            }
            if (restored != null) {
                fetchCurrentUserId(silent = true)
            }
        }
    }

    private fun observeSessionChanges() {
        viewModelScope.launch {
            repository.sessionFlow.collect { session ->
                val wasAuthenticated = _uiState.value.isAuthenticated
                _uiState.update {
                    it.copy(
                        isAuthenticated = session != null,
                        currentUserEmail = session?.email ?: it.currentUserEmail,
                        currentUserId = if (session == null) null else it.currentUserId
                    )
                }
                if (wasAuthenticated && session == null && !_uiState.value.isRestoringSession) {
                    _events.emit(
                        AuthEvent.Navigate(
                            route = AuthRoutes.LOGIN,
                            popUpToRoute = AuthRoutes.HOME,
                            inclusive = true
                        )
                    )
                }
            }
        }
    }

    fun clearFieldError(key: String) {
        if (_uiState.value.fieldErrors.containsKey(key)) {
            _uiState.update { it.copy(fieldErrors = it.fieldErrors - key) }
        }
    }

    fun onLoginEmailChange(value: String) {
        _uiState.update { it.copy(loginEmail = value, fieldErrors = it.fieldErrors - "loginEmail" - "email") }
    }

    fun onLoginPasswordChange(value: String) {
        _uiState.update { it.copy(loginPassword = value, fieldErrors = it.fieldErrors - "loginPassword" - "password") }
    }

    fun onRememberMeChange(checked: Boolean) {
        _uiState.update { it.copy(rememberMe = checked) }
    }

    fun onSignUpEmailChange(value: String) {
        _uiState.update { it.copy(signUpEmail = value, fieldErrors = it.fieldErrors - "signUpEmail" - "email") }
    }

    fun onChangeSignUpEmail() {
        signUpTimerJob?.cancel()
        _uiState.update {
            it.copy(
                signUpEmailSubmitted = false,
                signUpOtp = "",
                signUpOtpSecondsLeft = 0,
                fieldErrors = it.fieldErrors - "signUpOtp" - "signUpEmail"
            )
        }
    }

    fun onSignUpOtpChange(value: String) {
        _uiState.update { it.copy(signUpOtp = value, fieldErrors = it.fieldErrors - "signUpOtp") }
    }

    fun onSignUpFullNameChange(value: String) {
        _uiState.update { it.copy(signUpFullName = value, fieldErrors = it.fieldErrors - "fullName") }
    }

    fun onSignUpPasswordChange(value: String) {
        _uiState.update { it.copy(signUpPassword = value, fieldErrors = it.fieldErrors - "signUpPassword" - "password") }
    }

    fun onSignUpConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(signUpConfirmPassword = value, fieldErrors = it.fieldErrors - "signUpConfirmPassword") }
    }

    fun onResetEmailChange(value: String) {
        _uiState.update { it.copy(resetEmail = value, fieldErrors = it.fieldErrors - "resetEmail" - "email") }
    }

    fun onResetOtpChange(value: String) {
        _uiState.update { it.copy(resetOtp = value, fieldErrors = it.fieldErrors - "resetOtp") }
    }

    fun onResetNewPasswordChange(value: String) {
        _uiState.update { it.copy(resetNewPassword = value, fieldErrors = it.fieldErrors - "newPassword") }
    }

    fun onResetConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(resetConfirmPassword = value, fieldErrors = it.fieldErrors - "confirmNewPassword") }
    }

    fun requestSignUpOtp() {
        val email = _uiState.value.signUpEmail.trim()
        if (!isValidEmail(email)) {
            _uiState.update { it.copy(fieldErrors = mapOf("signUpEmail" to "Please enter a valid email address.")) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, fieldErrors = emptyMap()) }
            when (val res = repository.requestRegistrationOtp(email)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            signUpEmail = email,
                            signUpEmailSubmitted = true,
                            signUpOtp = ""
                        )
                    }
                    startSignUpCountdown()
                    _events.emit(AuthEvent.ShowMessage(res.data.message))
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            fieldErrors = res.error.fieldErrors.mapKeys { (k, _) ->
                                if (k == "email") "signUpEmail" else k
                            }
                        )
                    }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    fun verifySignUpOtp() {
        val email = _uiState.value.signUpEmail.trim()
        val otp = _uiState.value.signUpOtp.trim()
        if (otp.length != 6) {
            _uiState.update { it.copy(fieldErrors = mapOf("signUpOtp" to "Please enter the 6-digit OTP.")) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, fieldErrors = emptyMap()) }
            when (val res = repository.verifyRegistrationOtp(email, otp)) {
                is AuthResult.Success -> {
                    if (res.data.verified) {
                        signUpTimerJob?.cancel()
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                signUpEmail = res.data.email
                            )
                        }
                        _events.emit(AuthEvent.Navigate(AuthRoutes.CREATE_ACCOUNT))
                    } else {
                        _uiState.update { it.copy(isLoading = false) }
                        _events.emit(AuthEvent.ShowMessage(res.data.message))
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, fieldErrors = mapOf("signUpOtp" to res.error.message)) }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    fun completeRegistration() {
        val state = _uiState.value
        val errors = mutableMapOf<String, String>()
        if (state.signUpFullName.trim().length < 2) {
            errors["fullName"] = "Full name must contain at least 2 characters."
        }
        if (state.signUpPassword.length < 8) {
            errors["signUpPassword"] = "Password must contain at least 8 characters."
        }
        if (state.signUpConfirmPassword != state.signUpPassword) {
            errors["signUpConfirmPassword"] = "Passwords do not match."
        }
        if (errors.isNotEmpty()) {
            _uiState.update { it.copy(fieldErrors = errors) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, fieldErrors = emptyMap()) }
            when (val res = repository.registerAndLogin(state.signUpFullName, state.signUpEmail, state.signUpPassword)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isAuthenticated = true,
                            currentUserEmail = state.signUpEmail.trim(),
                            signUpFullName = "",
                            signUpPassword = "",
                            signUpConfirmPassword = "",
                            signUpOtp = "",
                            signUpEmailSubmitted = false
                        )
                    }
                    fetchCurrentUserId(silent = true)
                    _events.emit(
                        AuthEvent.Navigate(
                            route = AuthRoutes.HOME,
                            popUpToRoute = AuthRoutes.WELCOME,
                            inclusive = true
                        )
                    )
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            fieldErrors = res.error.fieldErrors.mapKeys { (k, _) ->
                                if (k == "password") "signUpPassword" else k
                            }
                        )
                    }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    fun login() {
        val state = _uiState.value
        val email = state.loginEmail.trim()
        val errors = mutableMapOf<String, String>()
        if (!isValidEmail(email)) {
            errors["loginEmail"] = "Please enter a valid email address."
        }
        if (state.loginPassword.isBlank()) {
            errors["loginPassword"] = "Password is required."
        }
        if (errors.isNotEmpty()) {
            _uiState.update { it.copy(fieldErrors = errors) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, fieldErrors = emptyMap()) }
            when (val res = repository.login(email, state.loginPassword, state.rememberMe)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isAuthenticated = true,
                            currentUserEmail = email,
                            loginPassword = ""
                        )
                    }
                    fetchCurrentUserId(silent = true)
                    _events.emit(
                        AuthEvent.Navigate(
                            route = AuthRoutes.HOME,
                            popUpToRoute = AuthRoutes.WELCOME,
                            inclusive = true
                        )
                    )
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            fieldErrors = res.error.fieldErrors.mapKeys { (k, _) ->
                                when (k) {
                                    "email" -> "loginEmail"
                                    "password" -> "loginPassword"
                                    else -> k
                                }
                            }
                        )
                    }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    fun authenticateWithGoogleIdToken(idToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, fieldErrors = emptyMap()) }
            when (val res = repository.loginWithGoogle(idToken)) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, isAuthenticated = true) }
                    fetchCurrentUserId(silent = true)
                    _events.emit(
                        AuthEvent.Navigate(
                            route = AuthRoutes.HOME,
                            popUpToRoute = AuthRoutes.WELCOME,
                            inclusive = true
                        )
                    )
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    fun showMessage(message: String) {
        viewModelScope.launch {
            _events.emit(AuthEvent.ShowMessage(message))
        }
    }

    fun sendForgotPasswordOtp(navigateToVerify: Boolean = true) {
        val email = _uiState.value.resetEmail.trim()
        if (!isValidEmail(email)) {
            _uiState.update { it.copy(fieldErrors = mapOf("resetEmail" to "Please provide a valid email address.")) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, fieldErrors = emptyMap()) }
            when (val res = repository.requestPasswordResetOtp(email)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            resetEmail = email,
                            resetOtp = ""
                        )
                    }
                    startResetCountdown()
                    _events.emit(AuthEvent.ShowMessage(res.data.message))
                    if (navigateToVerify) {
                        _events.emit(AuthEvent.Navigate(AuthRoutes.VERIFY_RESET_OTP))
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            fieldErrors = res.error.fieldErrors.mapKeys { (k, _) ->
                                if (k == "email") "resetEmail" else k
                            }
                        )
                    }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    fun verifyResetOtp() {
        val email = _uiState.value.resetEmail.trim()
        val otp = _uiState.value.resetOtp.trim()
        if (otp.length != 6) {
            _uiState.update { it.copy(fieldErrors = mapOf("resetOtp" to "Please enter the 6-digit OTP.")) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, fieldErrors = emptyMap()) }
            when (val res = repository.verifyPasswordResetOtp(email, otp)) {
                is AuthResult.Success -> {
                    if (res.data.verified) {
                        resetTimerJob?.cancel()
                        _uiState.update { it.copy(isLoading = false) }
                        _events.emit(AuthEvent.Navigate(AuthRoutes.CHANGE_PASSWORD))
                    } else {
                        _uiState.update { it.copy(isLoading = false) }
                        _events.emit(AuthEvent.ShowMessage(res.data.message))
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, fieldErrors = mapOf("resetOtp" to res.error.message)) }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    fun submitNewPassword() {
        val state = _uiState.value
        val errors = mutableMapOf<String, String>()
        if (state.resetNewPassword.length < 8) {
            errors["newPassword"] = "Password must be at least 8 characters long."
        }
        if (state.resetConfirmPassword != state.resetNewPassword) {
            errors["confirmNewPassword"] = "Passwords do not match."
        }
        if (errors.isNotEmpty()) {
            _uiState.update { it.copy(fieldErrors = errors) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, fieldErrors = emptyMap()) }
            when (val res = repository.resetPassword(state.resetEmail, state.resetNewPassword)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginEmail = state.resetEmail,
                            resetNewPassword = "",
                            resetConfirmPassword = "",
                            resetOtp = ""
                        )
                    }
                    _events.emit(
                        AuthEvent.Navigate(
                            route = AuthRoutes.PASSWORD_UPDATED,
                            popUpToRoute = AuthRoutes.FORGOT_PASSWORD,
                            inclusive = true
                        )
                    )
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, fieldErrors = res.error.fieldErrors) }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    fun fetchCurrentUserId(silent: Boolean = false) {
        viewModelScope.launch {
            if (!silent) {
                _uiState.update { it.copy(isLoading = true) }
            }
            when (val res = repository.fetchCurrentUserId()) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, currentUserId = res.data) }
                    if (!silent) {
                        _events.emit(AuthEvent.ShowMessage("Session verified with backend."))
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    if (!silent) {
                        _events.emit(AuthEvent.ShowMessage(res.error.message))
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val res = repository.logout()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isAuthenticated = false,
                    currentUserId = null
                )
            }
            if (res is AuthResult.Success) {
                _events.emit(AuthEvent.ShowMessage(res.data))
            }
            _events.emit(
                AuthEvent.Navigate(
                    route = AuthRoutes.WELCOME,
                    popUpToRoute = AuthRoutes.HOME,
                    inclusive = true
                )
            )
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val res = repository.deleteAccount()) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isAuthenticated = false,
                            currentUserId = null,
                            loginEmail = ""
                        )
                    }
                    _events.emit(AuthEvent.ShowMessage(res.data))
                    _events.emit(
                        AuthEvent.Navigate(
                            route = AuthRoutes.WELCOME,
                            popUpToRoute = AuthRoutes.HOME,
                            inclusive = true
                        )
                    )
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(AuthEvent.ShowMessage(res.error.message))
                }
            }
        }
    }

    private fun startSignUpCountdown() {
        signUpTimerJob?.cancel()
        signUpTimerJob = viewModelScope.launch {
            for (sec in OTP_COUNTDOWN_SECONDS downTo 0) {
                _uiState.update { it.copy(signUpOtpSecondsLeft = sec) }
                if (sec > 0) delay(1_000L)
            }
        }
    }

    private fun startResetCountdown() {
        resetTimerJob?.cancel()
        resetTimerJob = viewModelScope.launch {
            for (sec in OTP_COUNTDOWN_SECONDS downTo 0) {
                _uiState.update { it.copy(resetOtpSecondsLeft = sec) }
                if (sec > 0) delay(1_000L)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
