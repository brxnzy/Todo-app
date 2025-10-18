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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.AuthState
import com.example.todoapp.AuthViewModel
import com.example.todoapp.ui.theme.TodoAppTheme


@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    onClickAble: () -> Unit = {},
    onClickLogin: () -> Unit = {}
) {
    val authState by viewModel.authState.collectAsState()
    var username by remember { mutableStateOf(value = "") }
    var email by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    var passwordVisible by remember { mutableStateOf(value = false) }
    var error by remember { mutableStateOf(value = false) }

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
                    text = "Crear una cuenta",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(text = "Email")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f),
                    isError = error,
                    colors = TextFieldDefaults.colors(
                        errorLabelColor = Color.Red,
                        focusedContainerColor = Color.Transparent,

                        unfocusedContainerColor = Color.Transparent,
                    )
                )

                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = {
                        Text(text = "Usuario")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f),
                    isError = error,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        errorLabelColor = Color.Red,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    )
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(text = "Contraseña")
                    },
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f),
                    isError = error,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        TextButton(onClick = { passwordVisible = !passwordVisible }) {
                            Text(text = if (passwordVisible) "Ocultar" else "Mostrar")
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        errorLabelColor = Color.Red,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    )
                )

                Button(
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                            viewModel.signUp(email, password, username)
//                            onClickAble()
                        } else {
                            error = true
                        }
                    },
                    shape = RoundedCornerShape(size = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = authState !is AuthState.Loading)
                {
                    Text(
                        text = "Registrarse",
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(all = 5.dp)
                    )
                }
            }

            when (authState) {
                is AuthState.Loading -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                is AuthState.Error -> {
                    val message = (authState as AuthState.Error).message
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message,
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }

                is AuthState.Success -> {
                    LaunchedEffect(Unit) {
                        onClickAble()
                    }
                }

                AuthState.Idle -> Unit
            }

            // Validación de campos vacíos
            if (error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Llena todos los campos",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "¿Ya tienes una cuenta?",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onClickLogin() }
            )
        }
    }
}



//@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = true)
//@Composable
//fun RegisterPreview() {
//    RegisterScreen()
//}

