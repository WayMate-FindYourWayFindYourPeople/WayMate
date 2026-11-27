package com.waymate.app.ui.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.waymate.app.BuildConfig

sealed class GoogleSignInResult {
    data class Success(val idToken: String) : GoogleSignInResult()
    data object Cancelled : GoogleSignInResult()
    data class Error(val message: String) : GoogleSignInResult()
}

object GoogleSignInHelper {

    private const val PLACEHOLDER_CLIENT_ID = "YOUR_GOOGLE_WEB_CLIENT_ID.apps.googleusercontent.com"

    suspend fun signIn(activityContext: Context): GoogleSignInResult {
        val webClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID.trim()
        if (webClientId.isBlank() || webClientId == PLACEHOLDER_CLIENT_ID) {
            return GoogleSignInResult.Error(
                "Google Web Client ID is not configured. Set GOOGLE_WEB_CLIENT_ID in android-app/local.properties."
            )
        }

        val credentialManager = CredentialManager.create(activityContext)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(
                context = activityContext,
                request = request
            )
            val credential = result.credential
            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleCredential = GoogleIdTokenCredential.createFrom(credential.data)
                GoogleSignInResult.Success(googleCredential.idToken)
            } else {
                GoogleSignInResult.Error("Unsupported credential type returned by Google Sign-In.")
            }
        } catch (_: GetCredentialCancellationException) {
            GoogleSignInResult.Cancelled
        } catch (_: NoCredentialException) {
            GoogleSignInResult.Error("No Google account available on this device.")
        } catch (e: GoogleIdTokenParsingException) {
            GoogleSignInResult.Error(e.localizedMessage ?: "Unable to parse Google ID token.")
        } catch (e: GetCredentialException) {
            GoogleSignInResult.Error(e.localizedMessage ?: "Google Sign-In failed.")
        } catch (e: Exception) {
            GoogleSignInResult.Error(e.localizedMessage ?: "Google Sign-In failed.")
        }
    }
}
