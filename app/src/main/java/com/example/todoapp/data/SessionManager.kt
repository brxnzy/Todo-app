package com.example.todoapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences



// SessionManager.kt
class SessionManager(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")


    private val TOKEN_KEY = stringPreferencesKey("accessToken")
    private val REFRESH_KEY = stringPreferencesKey("refreshToken")

    private val dataStore = context.dataStore


    suspend fun saveSession(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = accessToken
            prefs[REFRESH_KEY] = refreshToken
        }
    }


    val sessionFlow: Flow<Pair<String, String>?> = dataStore.data.map { prefs ->
        val access = prefs[TOKEN_KEY]
        val refresh = prefs[REFRESH_KEY]
        if (access != null && refresh != null) access to refresh else null
    }

    // Borrar sesión
    suspend fun clearSession() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
