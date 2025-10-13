package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoapp.ui.theme.TodoAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.screens.LoginScreen
import com.example.todoapp.screens.RegisterScreen
import com.example.todoapp.screens.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                Scaffold(
                    bottomBar = {
                        BottomAppBar {
                            // comming soon bottomAppBar
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainNavigation(modifier : Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register") {

        composable(route = "splash") {
            SplashScreen()
        }
        composable(route = "register") {
            RegisterScreen(modifier = Modifier) {
                /* lambda de la funcion onCLickAble
                para manejar el boton del register */
            }
        }

        composable(route = "login") {
            LoginScreen(modifier = Modifier) {
                /* lambda de la funcion onCLickAble
                para manejar el boton del login */
            }
        }

    }
}
