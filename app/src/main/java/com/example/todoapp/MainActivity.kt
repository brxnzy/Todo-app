package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.data.supabase
import com.example.todoapp.screens.HomeScreen
import com.example.todoapp.screens.LoginScreen
import com.example.todoapp.screens.RegisterScreen
import com.example.todoapp.screens.Splash
import com.example.todoapp.ui.theme.TodoTheme
import io.github.jan.supabase.auth.Auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                val navController = rememberNavController()
                val authviewModel = remember { AuthViewModel(supabase) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation(
                        authviewModel,
                        navController,
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavigation(authviewModel: AuthViewModel,navController: NavHostController, modifier: Modifier) {

    NavHost(navController = navController, startDestination = "splash") {

        composable(route = "splash") {
            Splash(navController)
        }

        composable(route = "register") {

            RegisterScreen(viewModel= authviewModel,modifier = Modifier, onClickAble = {
                navController.navigate(route = "login")
            }, onClickLogin = {
                navController.navigate(route = "login")
            })
        }

        composable(route = "login") {
            LoginScreen(authviewModel,modifier = Modifier, onClickAble = {
                navController.navigate(route = "home")
            }, onClickRegister = {
                navController.navigate(route = "register")
            })
        }

        composable(route = "home") {
            HomeScreen(authviewModel,modifier = Modifier, fabOnClick = {
                navController.navigate(route = "createNote")
            }, navController = navController)
        }

    }
}
