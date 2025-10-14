package com.example.todoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R


@Preview(showSystemUi = true)
@Composable
fun RegisterScreen(modifier : Modifier = Modifier, onClickAble: () -> Unit = {}) {
    // estados del formulario para mandarlos a la db
    var username by remember { mutableStateOf(value = "") }
    var email by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }




    // formulario del register simple (email, username, password)
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {

        Text(text = "Registrate",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier)

        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email")
            }
        )

        TextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text(text = "Username")
            }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = "password")
            }
        )

        Button(onClick = onClickAble) {
            Text(text = "Registrarse")
        }

    }
}





