package com.example.todoapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.models.Profile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.result.PostgrestResult
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

    fun signUp(email: String, password: String, username: String) {
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
                Log.d("AuthViewModel", "User: ${result}")



                _authState.value = AuthState.Success
                _currentUser.value = Profile(
                    id = result?.id ?: "",
                    email = email,
                    username = username
                )
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
                Log.e("AuthViewModel", "=== Exception en signUp ===", e)
            }
        }
    }
    private suspend fun loadUserProfile(userId: String) {
        try {
            val profile = supabase.from("profiles")
                .select(columns = Columns.list("id, username, email"))
                {
                    filter {
                        eq("id", userId)
                    }
                }
                .decodeSingle<Profile>()

            _currentUser.value = profile
        } catch (e: Exception) {
            _authState.value = AuthState.Error("Error al cargar perfil")
        }
    }



}




sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}