package com.example.todoapp.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://omvjlfgkbdbfzlhlqytp.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9tdmpsZmdrYmRiZnpsaGxxeXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjAzNTAzMzYsImV4cCI6MjA3NTkyNjMzNn0.AsTnHcmP82a2S2tJ053BJ5Xp7ixyGiaOe_TIHvS87qA"
) {
    install(Auth)
    install(Postgrest)

}


