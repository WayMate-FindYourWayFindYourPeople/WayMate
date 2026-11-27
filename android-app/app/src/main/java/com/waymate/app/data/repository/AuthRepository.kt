package com.waymate.app.data.repository

import com.waymate.app.data.local.AuthSession
import com.waymate.app.data.local.TokenManager
import com.waymate.app.data.remote.ApiErrorParser
import com.waymate.app.data.remote.AuthApiService
import com.waymate.app.data.remote.ParsedApiError
import com.waymate.app.data.remote.dto.ForgotPasswordRequest
import com.waymate.app.data.remote.dto.GoogleLoginRequest
import com.waymate.app.data.remote.dto.LoginRequest
import com.waymate.app.data.remote.dto.LoginResponse
import com.waymate.app.data.remote.dto.RefreshTokenRequest
import com.waymate.app.data.remote.dto.RegisterRequest
import com.waymate.app.data.remote.dto.RequestOtpRequest
import com.waymate.app.data.remote.dto.RequestOtpResponse
import com.waymate.app.data.remote.dto.ResetPasswordRequest
import com.waymate.app.data.remote.dto.VerifyOtpRequest
import com.waymate.app.data.remote.dto.VerifyOtpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response
import java.io.IOException

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val error: ParsedApiError) : AuthResult<Nothing>()
}

class AuthRepository(
    private val api: AuthApiService,
    private val tokenManager: TokenManager
) {
    val sessionFlow: StateFlow<AuthSession?> = tokenManager.sessionFlow
    val rememberedEmailFlow: Flow<String> = tokenManager.rememberedEmailFlow
    val rememberMeFlow: Flow<Boolean> = tokenManager.rememberMeFlow

    suspend fun restoreSession(): AuthSession? = tokenManager.restoreSession()

    suspend fun requestRegistrationOtp(email: String): AuthResult<RequestOtpResponse> =
        executeCall { api.requestOtp(RequestOtpRequest(email = email.trim())) }

    suspend fun verifyRegistrationOtp(email: String, otp: String): AuthResult<VerifyOtpResponse> =
        executeCall { api.verifyOtp(VerifyOtpRequest(email = email.trim(), otp = otp.trim())) }

    suspend fun registerAndLogin(
        fullName: String,
        email: String,
        password: String
    ): AuthResult<LoginResponse> {
        val trimmedEmail = email.trim()
        val registerResult = executeCall {
            api.register(
                RegisterRequest(
                    fullName = fullName.trim(),
                    email = trimmedEmail,
                    password = password
                )
            )
        }
        return when (registerResult) {
            is AuthResult.Error -> registerResult
            is AuthResult.Success -> login(email = trimmedEmail, password = password, rememberMe = true)
        }
    }

    suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): AuthResult<LoginResponse> {
        val trimmedEmail = email.trim()
        val result = executeCall {
            api.login(LoginRequest(email = trimmedEmail, password = password))
        }
        if (result is AuthResult.Success) {
            tokenManager.saveSession(
                session = AuthSession(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    tokenType = result.data.tokenType,
                    email = trimmedEmail
                ),
                rememberMe = rememberMe
            )
        }
        return result
    }

    suspend fun loginWithGoogle(idToken: String): AuthResult<LoginResponse> {
        val result = executeCall { api.googleLogin(GoogleLoginRequest(idToken = idToken)) }
        if (result is AuthResult.Success) {
            tokenManager.saveSession(
                session = AuthSession(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    tokenType = result.data.tokenType,
                    email = null
                ),
                rememberMe = true
            )
        }
        return result
    }

    suspend fun requestPasswordResetOtp(email: String): AuthResult<RequestOtpResponse> =
        executeCall { api.forgotPassword(ForgotPasswordRequest(email = email.trim())) }

    suspend fun verifyPasswordResetOtp(email: String, otp: String): AuthResult<VerifyOtpResponse> =
        executeCall { api.verifyResetOtp(VerifyOtpRequest(email = email.trim(), otp = otp.trim())) }

    suspend fun resetPassword(email: String, newPassword: String): AuthResult<String> =
        executeCall {
            api.resetPassword(
                ResetPasswordRequest(
                    email = email.trim(),
                    newPassword = newPassword
                )
            )
        }

    suspend fun fetchCurrentUserId(): AuthResult<String> {
        return when (val res = executeCall { api.getCurrentUserId() }) {
            is AuthResult.Success -> AuthResult.Success(res.data.trim().removeSurrounding("\""))
            is AuthResult.Error -> res
        }
    }

    suspend fun logout(): AuthResult<String> {
        val refreshToken = tokenManager.currentRefreshToken()
        val result = if (!refreshToken.isNullOrBlank()) {
            executeCall { api.logout(RefreshTokenRequest(refreshToken)) }
        } else {
            AuthResult.Success("Logged out locally.")
        }
        tokenManager.clearSession()
        return result
    }

    suspend fun deleteAccount(): AuthResult<String> {
        val result = executeCall { api.deleteAccount() }
        if (result is AuthResult.Success) {
            tokenManager.clearSession()
        }
        return result
    }

    private suspend fun <T> executeCall(call: suspend () -> Response<T>): AuthResult<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    AuthResult.Success(body)
                } else {
                    AuthResult.Error(ParsedApiError("Empty response received from server.", statusCode = response.code()))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                AuthResult.Error(ApiErrorParser.parse(response.code(), errorBody))
            }
        } catch (_: IOException) {
            AuthResult.Error(
                ParsedApiError("Cannot reach WayMate server. Make sure the backend is running on port 8080.")
            )
        } catch (e: Exception) {
            AuthResult.Error(
                ParsedApiError(e.localizedMessage ?: "An unexpected error occurred.")
            )
        }
    }
}
