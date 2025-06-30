package com.example.preferdatabase.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.preferdatabase.data.LoginPrefs


@Composable
fun LoginScreen(
    vm: AuthViewModel = viewModel(),
    onSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var pwd   by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(email, { email = it }, label = { Text("Email") })
        OutlinedTextField(
            pwd, { pwd = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))

        if (vm.loading) CircularProgressIndicator()
        vm.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button(
            onClick = { vm.login(email.trim(), pwd, onSuccess) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !vm.loading
        ) { Text("Login") }

        TextButton(
            onClick = { vm.register(email.trim(), pwd, onSuccess) },
            enabled = !vm.loading
        ) { Text("New? Create Account") }
    }
}