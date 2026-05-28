package com.example.weatherapp2

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp2.ui.theme.WeatherAPP2Theme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAPP2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    val modifier = modifier.fillMaxWidth(fraction = 0.9f)
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConfirm by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as Activity

    Column(
        modifier = modifier.padding(24.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(text = "Criar conta", fontSize = 24.sp)
        Spacer(modifier = modifier.size(12.dp))
        OutlinedTextField(
            value = name,
            label = { Text("Nome") },
            modifier = modifier,
            onValueChange = { name = it }
        )
        Spacer(modifier = modifier.size(12.dp))
        OutlinedTextField(
            value = email,
            label = { Text("E-mail") },
            modifier = modifier,
            onValueChange = { email = it }
        )
        Spacer(modifier = modifier.size(12.dp))
        OutlinedTextField(
            value = password,
            label = { Text("Senha") },
            modifier = modifier,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = modifier.size(12.dp))
        OutlinedTextField(
            value = passwordConfirm,
            label = { Text("Confirmar senha") },
            modifier = modifier,
            onValueChange = { passwordConfirm = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = modifier.size(12.dp))
        Row(
            modifier = modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    Toast.makeText(activity, "Registro OK!", Toast.LENGTH_LONG).show()
                    activity.finish()
                },
                enabled = name.isNotEmpty() && email.isNotEmpty()
                        && password.isNotEmpty()
                        && password == passwordConfirm
            ) {
                Text("Registrar")
            }
            Button(onClick = { name = ""; email = ""; password = ""; passwordConfirm = "" }) {
                Text("Limpar")
            }
        }
    }
}
