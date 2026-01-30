package com.example.week3.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week3.viewmodel.TaskViewModel
import com.example.week3.model.Task
import androidx.compose.runtime.collectAsState
import com.example.week3.viewmodel.TaskFilter

@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    val uiState by taskViewModel.uiState.collectAsState()
    val filteredTasks = remember(uiState.tasks, uiState.filter) {
        taskViewModel.getFilteredTasks()
    }

    val selectedTask by taskViewModel.selectedTask.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "My Tasks",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    taskViewModel.sortByDueDate()
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
                    Text(text = "Sort by due date")
                }

                TaskFilter.entries.forEach { filter ->
                    FilterChip(
                        selected = uiState.filter == filter,
                        onClick = { taskViewModel.setFilter(filter) },
                        label = { Text(filter.name) }
                    )
                }
            }

            Column {
                var text by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = text,
                    singleLine = true,
                    onValueChange = { text = it },
                    label = { Text("Task") }
                )
                Button(onClick = {
                    taskViewModel.addTask(text)
                    text = ""
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
                    Text(text = "Add task")
                }
            }

            LazyColumn {
                items(items = filteredTasks, key = { it.id }) { item ->
                    TaskRow(
                        task = item,
                        onToggleDone = {
                            taskViewModel.toggleDone(item.id)
                        },
                        onClick = {
                            taskViewModel.selectTask(item)
                        },
                        onDelete = {
                            taskViewModel.removeTask(item.id)
                        }
                    )
                }
            }
        }

        if (selectedTask != null) {
            DetailDialog(
                task = selectedTask!!,
                onClose = {
                    taskViewModel.unselectTask()
                },
                onUpdate = {
                    taskViewModel.updateTask(it)
                }
            )
        }
    }
}

@Composable
fun TaskRow(
    task: Task,
    onClick: () -> Unit,
    onToggleDone: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable {onClick() }) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
    ) {
        androidx.compose.material3.Checkbox(
            checked = task.done,
            onCheckedChange = { onToggleDone() }
        )

        Column(modifier = Modifier
            .weight(1f)
            .padding(start = 8.dp)
        )
        {
            Text(text = task.title)
            Text(text = task.dueDate)
        }

        Button(onClick = onDelete,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
            Text("Delete")
        }
    }
    }
}
