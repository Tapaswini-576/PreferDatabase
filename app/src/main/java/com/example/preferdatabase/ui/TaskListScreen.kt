package com.example.preferdatabase.ui



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.preferdatabase.data.LoginPrefs
import com.example.preferdatabase.data.local.TaskEntity
import com.google.firebase.auth.FirebaseAuth

import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onLogout: () -> Unit) {
    TopAppBar(
        title = { Text("Tasks") },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut()
                LoginPrefs.clear()          // wipe email + token
            }) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
            }
        }
    )
}

@Composable
fun TaskListScreen(
    vm: TaskViewModel,
    onLogout: () -> Unit
) {
    val tasks by vm.tasks.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        TopBar(
            onLogout = onLogout
        )

        //INPUT
        var newTitle by remember { mutableStateOf("") }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("New task") },
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { if (newTitle.isNotBlank()) { vm.add(newTitle); newTitle = "" } }
            ) { Icon(Icons.Default.Add, null) }
        }

        Spacer(Modifier.height(8.dp))

        //TASK LIST -->
        LazyColumn(Modifier.fillMaxSize()) {
            items(tasks, key = { it.id }) { task ->
                TaskRow(task,
                    onToggle = { vm.toggle(task) },
                    onDelete = { vm.delete(task) }
                )
            }
        }
    }
}

@Composable
private fun TaskRow(
    task: TaskEntity,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = task.done, onCheckedChange = { onToggle() })
        Text(
            text = task.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, null)
        }
    }
}