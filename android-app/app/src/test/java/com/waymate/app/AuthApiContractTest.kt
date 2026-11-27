package com.waymate.app

import com.google.gson.Gson
import com.waymate.app.data.remote.ApiErrorParser
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthApiContractTest {

    private val gson = Gson()

    @Test
    fun `api error parser extracts GlobalExceptionHandler message`() {
        val raw = """{"message":"Invalid email or password."}"""
        val parsed = ApiErrorParser.parse(401, raw)
        assertEquals("Invalid email or password.", parsed.message)
        assertTrue(parsed.fieldErrors.isEmpty())
        assertEquals(401, parsed.statusCode)
    }

    @Test
    fun `api error parser extracts MethodArgumentNotValid field map`() {
        val raw = """{"email":"Please enter a valid email address","password":"Password must contain at least 8 characters"}"""
        val parsed = ApiErrorParser.parse(400, raw)
        assertEquals("Please enter a valid email address", parsed.fieldErrors["email"])
        assertEquals("Password must contain at least 8 characters", parsed.fieldErrors["password"])
        assertTrue(parsed.message.contains("Please enter a valid email address"))
    }

    @Test
    fun `dtos match backend json field names`() {
        val registerJson = gson.toJson(RegisterRequest("Alex Rivera", "alex@example.com", "secret123"))
        assertTrue(registerJson.contains("\"fullName\":\"Alex Rivera\""))
        assertTrue(registerJson.contains("\"email\":\"alex@example.com\""))
        assertTrue(registerJson.contains("\"password\":\"secret123\""))

        val resetJson = gson.toJson(ResetPasswordRequest("alex@example.com", "newpass123"))
        assertTrue(resetJson.contains("\"newPassword\":\"newpass123\""))

        val googleJson = gson.toJson(GoogleLoginRequest("eyJtoken"))
        assertTrue(googleJson.contains("\"idToken\":\"eyJtoken\""))

        val loginReqJson = gson.toJson(LoginRequest("alex@example.com", "secret123"))
        assertTrue(loginReqJson.contains("\"email\":\"alex@example.com\""))

        val otpReqJson = gson.toJson(RequestOtpRequest("alex@example.com"))
        assertTrue(otpReqJson.contains("\"email\":\"alex@example.com\""))

        val verifyReqJson = gson.toJson(VerifyOtpRequest("alex@example.com", "123456"))
        assertTrue(verifyReqJson.contains("\"otp\":\"123456\""))

        val forgotJson = gson.toJson(ForgotPasswordRequest("alex@example.com"))
        assertTrue(forgotJson.contains("\"email\":\"alex@example.com\""))

        val refreshReqJson = gson.toJson(RefreshTokenRequest("refresh-xyz"))
        assertTrue(refreshReqJson.contains("\"refreshToken\":\"refresh-xyz\""))

        val loginResp = gson.fromJson(
            """{"accessToken":"jwt1","tokenType":"Bearer","refreshToken":"ref1"}""",
            LoginResponse::class.java
        )
        assertEquals("jwt1", loginResp.accessToken)
        assertEquals("Bearer", loginResp.tokenType)
        assertEquals("ref1", loginResp.refreshToken)

        val refreshResp = gson.fromJson(
            """{"accessToken":"jwt2","refreshToken":"ref2","tokenType":"Bearer"}""",
            RefreshTokenResponse::class.java
        )
        assertEquals("jwt2", refreshResp.accessToken)

        val reqOtpResp = gson.fromJson("""{"message":"OTP sent"}""", RequestOtpResponse::class.java)
        assertEquals("OTP sent", reqOtpResp.message)

        val verifyResp = gson.fromJson(
            """{"message":"Verified","verified":true,"email":"alex@example.com"}""",
            VerifyOtpResponse::class.java
        )
        assertTrue(verifyResp.verified)
        assertEquals("alex@example.com", verifyResp.email)
    }
}
