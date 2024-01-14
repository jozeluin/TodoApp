package com.cursokotlin.todoapp.addtasks.domain

import com.cursokotlin.todoapp.addtasks.data.TaskRepository
import com.cursokotlin.todoapp.addtasks.ui.model.TasksModel
import javax.inject.Inject

class DeleteTaskUseCae  @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke (tasksModel: TasksModel){
        taskRepository.delete(tasksModel)
    }
}