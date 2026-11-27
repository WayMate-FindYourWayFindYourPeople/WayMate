package com.waymate.app.data.remote

import com.google.gson.JsonParser

data class ParsedApiError(
    val message: String,
    val fieldErrors: Map<String, String> = emptyMap(),
    val statusCode: Int? = null
)

object ApiErrorParser {

    fun parse(statusCode: Int, rawBody: String?): ParsedApiError {
        if (rawBody.isNullOrBlank()) {
            return ParsedApiError(
                message = defaultMessageForStatus(statusCode),
                statusCode = statusCode
            )
        }

        return try {
            val element = JsonParser.parseString(rawBody)
            if (!element.isJsonObject) {
                return ParsedApiError(
                    message = rawBody.trim().ifBlank { defaultMessageForStatus(statusCode) },
                    statusCode = statusCode
                )
            }

            val obj = element.asJsonObject
            if (obj.has("message") && obj.get("message").isJsonPrimitive) {
                return ParsedApiError(
                    message = obj.get("message").asString,
                    statusCode = statusCode
                )
            }

            val fields = linkedMapOf<String, String>()
            for ((key, value) in obj.entrySet()) {
                if (value.isJsonPrimitive) {
                    fields[key] = value.asString
                }
            }
            val combined = if (fields.isNotEmpty()) {
                fields.values.joinToString("\n")
            } else {
                defaultMessageForStatus(statusCode)
            }
            ParsedApiError(
                message = combined,
                fieldErrors = fields,
                statusCode = statusCode
            )
        } catch (_: Exception) {
            ParsedApiError(
                message = rawBody.trim().ifBlank { defaultMessageForStatus(statusCode) },
                statusCode = statusCode
            )
        }
    }

    private fun defaultMessageForStatus(statusCode: Int): String = when (statusCode) {
        400 -> "Please check the entered details and try again."
        401 -> "Invalid credentials or expired session."
        403 -> "Access denied. Please verify your email first."
        404 -> "Requested record was not found."
        409 -> "An account with this email already exists."
        410 -> "Verification code or session has expired. Please request a new OTP."
        429 -> "Too many attempts. Please request a new OTP."
        502 -> "Email service is temporarily unavailable. Please try again."
        else -> "Something went wrong (HTTP $statusCode). Please try again."
    }
}
