package com.example.week3.model

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val dueDate: String,
    val done: Boolean = false
)
