package com.example.preferdatabase


import android.content.Context
import androidx.compose.runtime.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.preferdatabase.data.LoginPrefs
import com.example.preferdatabase.ui.LoginScreen
import com.example.preferdatabase.ui.TaskListScreen
import com.example.preferdatabase.ui.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.preferdatabase.data.remote.AuthState
import com.example.preferdatabase.ui.TaskViewModel
import com.example.preferdatabase.ui.theme.PreferDatabaseTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        LoginPrefs.init(applicationContext)

        setContent {
            PreferDatabaseTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MaterialTheme { AppRoot() }
                }
            }
        }
    }
}

@Composable
fun AppRoot(context: Context = LocalContext.current) {
    val loggedIn by AuthState.loggedInFlow.collectAsState()

    if (loggedIn) {
        val vm = viewModel<TaskViewModel>(
            factory = TaskViewModel.factory(context)
        )

        TaskListScreen(
            vm = vm,
            onLogout = {
                FirebaseAuth.getInstance().signOut()
            }
        )
    } else {
        val authVm = viewModel<AuthViewModel>()
        LoginScreen(
            vm = authVm,
            onSuccess = {}
        )
    }
}