package com.waymate.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RequestOtpRequest(
    @SerializedName("email") val email: String
)

data class RequestOtpResponse(
    @SerializedName("message") val message: String
)

data class VerifyOtpRequest(
    @SerializedName("email") val email: String,
    @SerializedName("otp") val otp: String
)

data class VerifyOtpResponse(
    @SerializedName("message") val message: String,
    @SerializedName("verified") val verified: Boolean,
    @SerializedName("email") val email: String
)

data class RegisterRequest(
    @SerializedName("fullName") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("tokenType") val tokenType: String,
    @SerializedName("refreshToken") val refreshToken: String
)

data class GoogleLoginRequest(
    @SerializedName("idToken") val idToken: String
)

data class RefreshTokenRequest(
    @SerializedName("refreshToken") val refreshToken: String
)

data class RefreshTokenResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("tokenType") val tokenType: String
)

data class ForgotPasswordRequest(
    @SerializedName("email") val email: String
)

data class ResetPasswordRequest(
    @SerializedName("email") val email: String,
    @SerializedName("newPassword") val newPassword: String
)
