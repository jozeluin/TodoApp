package com.cursokotlin.todoapp.addtasks.ui.model

data class TasksModel(
    val id: Long = System.currentTimeMillis(),
    val task: String,
    var selected: Boolean,
)
