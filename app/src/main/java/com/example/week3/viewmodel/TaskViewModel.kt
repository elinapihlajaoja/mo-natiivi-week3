package com.example.week3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week3.model.Task
import com.example.week3.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUIState())
    val uiState: StateFlow<TaskUIState> = _uiState.asStateFlow()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                _uiState.value = _uiState.value.copy(tasks = mockTasks, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false, errorMessage = e.message
                )
            }
        }
    }

    fun toggleDone(id: String) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map {
                if (it.id == id) it.copy(done = !it.done)
                else it
            }
        )
    }

    fun updateTask(update: Task) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map {
                if (it.id == update.id) update
                else it
            },
        )
        _selectedTask.value = null
    }

    fun removeTask(id: String) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.filter { it.id != id }
        )
    }

    fun addTask(newTitle: String) {
        val newTask = Task(
            title = newTitle,
            description = "",
            dueDate = "",
            done = false
        )
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks + newTask
        )
    }

    fun setFilter(filter: TaskFilter) {
        _uiState.value = _uiState.value.copy(filter = filter)
    }

    fun getFilteredTasks(): List<Task> {
        return when (_uiState.value.filter) {
            TaskFilter.ALL -> _uiState.value.tasks
            TaskFilter.COMPLETED -> _uiState.value.tasks.filter { it.done }
        }
    }

    fun sortByDueDate() {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.sortedBy { it.dueDate }
        )
    }

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun unselectTask() {
        _selectedTask.value = null
    }
}
