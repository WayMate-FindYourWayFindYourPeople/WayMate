package com.waymate.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "waymate_auth_prefs")

data class AuthSession(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val email: String? = null
)

class TokenManager(private val context: Context) {

    private object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val TOKEN_TYPE = stringPreferencesKey("token_type")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    private val _sessionFlow = MutableStateFlow<AuthSession?>(null)
    val sessionFlow: StateFlow<AuthSession?> = _sessionFlow.asStateFlow()

    val rememberedEmailFlow: Flow<String> = context.authDataStore.data.map { prefs ->
        prefs[Keys.USER_EMAIL].orEmpty()
    }

    val rememberMeFlow: Flow<Boolean> = context.authDataStore.data.map { prefs ->
        prefs[Keys.REMEMBER_ME] ?: true
    }

    suspend fun restoreSession(): AuthSession? {
        val prefs = context.authDataStore.data.first()
        val rememberMe = prefs[Keys.REMEMBER_ME] ?: true
        if (!rememberMe) {
            clearPersistedTokensOnly()
            _sessionFlow.value = null
            return null
        }

        val access = prefs[Keys.ACCESS_TOKEN]
        val refresh = prefs[Keys.REFRESH_TOKEN]
        if (access.isNullOrBlank() || refresh.isNullOrBlank()) {
            _sessionFlow.value = null
            return null
        }

        val session = AuthSession(
            accessToken = access,
            refreshToken = refresh,
            tokenType = prefs[Keys.TOKEN_TYPE] ?: "Bearer",
            email = prefs[Keys.USER_EMAIL]
        )
        _sessionFlow.value = session
        return session
    }

    fun currentAccessToken(): String? = _sessionFlow.value?.accessToken

    fun currentRefreshToken(): String? = _sessionFlow.value?.refreshToken

    suspend fun saveSession(session: AuthSession, rememberMe: Boolean) {
        _sessionFlow.value = session
        context.authDataStore.edit { prefs ->
            prefs[Keys.REMEMBER_ME] = rememberMe
            if (rememberMe) {
                prefs[Keys.ACCESS_TOKEN] = session.accessToken
                prefs[Keys.REFRESH_TOKEN] = session.refreshToken
                prefs[Keys.TOKEN_TYPE] = session.tokenType
                session.email?.let { prefs[Keys.USER_EMAIL] = it }
            } else {
                prefs.remove(Keys.ACCESS_TOKEN)
                prefs.remove(Keys.REFRESH_TOKEN)
                prefs.remove(Keys.TOKEN_TYPE)
                prefs.remove(Keys.USER_EMAIL)
            }
        }
    }

    suspend fun updateTokens(accessToken: String, refreshToken: String, tokenType: String) {
        val existing = _sessionFlow.value
        val updated = AuthSession(
            accessToken = accessToken,
            refreshToken = refreshToken,
            tokenType = tokenType,
            email = existing?.email
        )
        _sessionFlow.value = updated

        val prefs = context.authDataStore.data.first()
        val rememberMe = prefs[Keys.REMEMBER_ME] ?: true
        if (rememberMe) {
            context.authDataStore.edit { mutable ->
                mutable[Keys.ACCESS_TOKEN] = accessToken
                mutable[Keys.REFRESH_TOKEN] = refreshToken
                mutable[Keys.TOKEN_TYPE] = tokenType
            }
        }
    }

    suspend fun clearSession() {
        _sessionFlow.value = null
        clearPersistedTokensOnly()
    }

    private suspend fun clearPersistedTokensOnly() {
        context.authDataStore.edit { prefs ->
            prefs.remove(Keys.ACCESS_TOKEN)
            prefs.remove(Keys.REFRESH_TOKEN)
            prefs.remove(Keys.TOKEN_TYPE)
        }
    }
}
