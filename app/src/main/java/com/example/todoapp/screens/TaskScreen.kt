package com.example.todoapp.screens
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.TaskViewModel
import com.example.todoapp.models.Task

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.myTasks.collectAsState()


    LaunchedEffect(Unit) {
        Log.d("TAREAS", "LaunchedEffect - Llamando getTasks()")
        viewModel.getTasks()
    }

    LazyColumn {
        items(
            count = tasks.size
        ) { index ->
            Log.d("TAREAS", "Renderizando tarea $index: ${tasks[index].title}")
            TaskItem(task = tasks[index])
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = task.description)
            Text(text = "Prioridad: ${task.priority}")
        }
    }
}