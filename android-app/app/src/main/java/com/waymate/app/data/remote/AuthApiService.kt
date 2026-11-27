package com.waymate.app.data.remote

import com.waymate.app.data.remote.dto.ForgotPasswordRequest
import com.waymate.app.data.remote.dto.GoogleLoginRequest
import com.waymate.app.data.remote.dto.LoginRequest
import com.waymate.app.data.remote.dto.LoginResponse
import com.waymate.app.data.remote.dto.RefreshTokenRequest
import com.waymate.app.data.remote.dto.RefreshTokenResponse
import com.waymate.app.data.remote.dto.RegisterRequest
import com.waymate.app.data.remote.dto.RequestOtpRequest
import com.waymate.app.data.remote.dto.RequestOtpResponse
import com.waymate.app.data.remote.dto.ResetPasswordRequest
import com.waymate.app.data.remote.dto.VerifyOtpRequest
import com.waymate.app.data.remote.dto.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/auth/request-otp")
    suspend fun requestOtp(@Body request: RequestOtpRequest): Response<RequestOtpResponse>

    @POST("api/auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<String>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/google")
    suspend fun googleLogin(@Body request: GoogleLoginRequest): Response<LoginResponse>

    @POST("api/auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<RequestOtpResponse>

    @POST("api/auth/verify-reset-otp")
    suspend fun verifyResetOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

    @POST("api/auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<String>

    @POST("api/auth/refresh")
    suspend fun refresh(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

    @POST("api/auth/refresh")
    fun refreshSync(@Body request: RefreshTokenRequest): retrofit2.Call<RefreshTokenResponse>

    @GET("api/auth/me")
    suspend fun getCurrentUserId(): Response<String>

    @POST("api/auth/logout")
    suspend fun logout(@Body request: RefreshTokenRequest): Response<String>

    @DELETE("api/auth/delete-account")
    suspend fun deleteAccount(): Response<String>
}
