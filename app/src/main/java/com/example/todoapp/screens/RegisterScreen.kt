package com.example.todoapp.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.TodoAppTheme


@Composable
fun RegisterScreen(modifier: Modifier = Modifier, onClickAble: () -> Unit = {}) {

    var username by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    var error by remember { mutableStateOf(false) }





    TodoAppTheme {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crea una cuenta",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Usuario") },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    isError = error,
                    colors = OutlinedTextFieldDefaults.colors(
                        errorLabelColor =  Color.Red

                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Contraseña") },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    isError = error,

                    colors = OutlinedTextFieldDefaults.colors(
                        errorLabelColor =  Color.Red
                    )
                )

                Button(
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty()) {
                            onClickAble()
                        } else {
                            error = true
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Registrarse",
                        fontSize = 17.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
            if (error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Llena todos los campos",
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ya tienes una cuenta?",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { onClickAble() }
            )

        }
    }
}





