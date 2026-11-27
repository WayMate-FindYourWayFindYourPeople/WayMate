package com.waymate.app.data.remote

import com.waymate.app.BuildConfig
import com.waymate.app.data.local.TokenManager
import com.waymate.app.data.remote.dto.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private val PUBLIC_AUTH_PATHS = setOf(
    "/api/auth/register",
    "/api/auth/login",
    "/api/auth/refresh",
    "/api/auth/request-otp",
    "/api/auth/verify-otp",
    "/api/auth/forgot-password",
    "/api/auth/verify-reset-otp",
    "/api/auth/reset-password",
    "/api/auth/google"
)

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val path = original.url.encodedPath
        val token = tokenManager.currentAccessToken()

        val request = if (token.isNullOrBlank() || path in PUBLIC_AUTH_PATHS) {
            original
        } else {
            original.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        }
        return chain.proceed(request)
    }
}

class TokenAuthenticator(
    private val tokenManager: TokenManager,
    private val refreshApiProvider: () -> AuthApiService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.url.encodedPath in PUBLIC_AUTH_PATHS) {
            return null
        }
        if (responseCount(response) >= 2) {
            return null
        }

        val currentRefreshToken = tokenManager.currentRefreshToken() ?: return null

        synchronized(this) {
            val latestAccess = tokenManager.currentAccessToken()
            val requestAuth = response.request.header("Authorization")
            if (!latestAccess.isNullOrBlank() && requestAuth != "Bearer $latestAccess") {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $latestAccess")
                    .build()
            }

            val refreshResult = try {
                refreshApiProvider()
                    .refreshSync(RefreshTokenRequest(currentRefreshToken))
                    .execute()
            } catch (_: Exception) {
                null
            }

            val body = refreshResult?.body()
            if (refreshResult != null && refreshResult.isSuccessful && body != null) {
                runBlocking {
                    tokenManager.updateTokens(
                        accessToken = body.accessToken,
                        refreshToken = body.refreshToken,
                        tokenType = body.tokenType
                    )
                }
                return response.request.newBuilder()
                    .header("Authorization", "Bearer ${body.accessToken}")
                    .build()
            }

            runBlocking { tokenManager.clearSession() }
            return null
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}

object NetworkModule {

    fun createAuthApiService(tokenManager: TokenManager): AuthApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val plainRetrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val refreshService = plainRetrofit.create(AuthApiService::class.java)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(tokenManager))
            .authenticator(TokenAuthenticator(tokenManager) { refreshService })
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }
}
