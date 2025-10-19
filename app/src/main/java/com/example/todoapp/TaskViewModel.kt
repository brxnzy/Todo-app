package com.example.todoapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.models.Profile
import com.example.todoapp.models.Task
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import io.github.jan.supabase.auth.auth

import kotlinx.coroutines.launch

class TaskViewModel(private val supabase: SupabaseClient): ViewModel(){
    private val _myTasks = MutableStateFlow<List<Task>>(emptyList())
    val myTasks: StateFlow<List<Task>> = _myTasks.asStateFlow()

    init {
        Log.d("TAREAS", "TaskViewModel inicializado")
    }

    fun getTasks(){
        Log.d("TAREAS", "getTasks() llamado")
        viewModelScope.launch {
            try {
                val currentUser = supabase.auth.currentUserOrNull()
                Log.d("TAREAS", "Usuario actual: ${currentUser?.id}")
                if (currentUser == null) {
                    Log.e("TAREAS", "❌ NO HAY USUARIO LOGUEADO")
                    return@launch
                }

                Log.d("TAREAS", "Haciendo query a Supabase...")
                val tasks = supabase.from("tasks")
                    .select() {
                        filter {
                            eq("userId", currentUser.id)
                        }
                    }
                    .decodeList<Task>()

                Log.d("TAREAS", "✅ Query exitoso - Total tareas: ${tasks.size}")
                Log.d("TAREAS", "Tareas: $tasks")

                _myTasks.value = tasks
                Log.d("TAREAS", "StateFlow actualizado")

            } catch (e: Exception) {
                Log.e("TAREAS", "❌ ERROR: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }
}


