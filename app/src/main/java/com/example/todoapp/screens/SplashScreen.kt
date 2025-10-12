package com.example.todoapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen() {
    Column(modifier = Modifier
        .fillMaxSize()) {

    }
}



@Preview(showSystemUi = true)
@Composable
fun SplashScreenPreview(){
    SplashScreen()
}