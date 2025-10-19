package com.example.todoapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.SessionManager
import com.example.todoapp.models.Profile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthViewModel(private val supabase: SupabaseClient): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<Profile?>(null)
    val currentUser: StateFlow<Profile?> = _currentUser.asStateFlow()

    fun signUp(email: String, password: String, username: String, sessionManager: SessionManager) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading

                val result = supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                    data = buildJsonObject {
                        put("username", username)
                    }
                }
                Log.d("AuthViewModel", "=== Supabase signUp Result ===")
                Log.d("AuthViewModel", "User: $result")

                // Obtener la sesión actual después del registro
                val session = supabase.auth.currentSessionOrNull()
                if (session != null) {
                    sessionManager.saveSession(
                        accessToken = session.accessToken,
                        refreshToken = session.refreshToken
                    )
                }

                _authState.value = AuthState.Success
                _currentUser.value = Profile(
                    id = result?.id ?: "",
                    name = username
                )

            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
                Log.e("AuthViewModel", "=== Exception en signUp ===", e)
            }
        }
    }



    fun signIn(email: String, password: String, sessionManager: SessionManager) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading

                val result = supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }

                val user = supabase.auth.currentUserOrNull()
                if (user != null) {
                    // Guardar sesión en DataStore
                    val session = supabase.auth.currentSessionOrNull()
                    if (session != null) {
                        sessionManager.saveSession(
                            accessToken = session.accessToken,
                            refreshToken = session.refreshToken
                        )
                    }

                    // Traer profile
                    val profile = supabase.from("profiles")
                        .select(columns = Columns.list("id", "name")) {
                            filter { eq("id", user.id) }
                        }
                        .decodeSingle<Profile>()

                    _currentUser.value = profile
                    _authState.value = AuthState.Success
                }

            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
            }
        }
    }


    fun signOut() {
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
                _currentUser.value = null
                _authState.value = AuthState.Idle
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error en logout: ${e.message}")
            }
        }
    }





}




sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}