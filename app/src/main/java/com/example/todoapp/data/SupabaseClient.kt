package com.example.todoapp.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://omvjlfgkbdbfzlhlqytp.supabase.co",
    supabaseKey = "publishable-or-"
) {
    install(Auth)
    install(Postgrest)

}


