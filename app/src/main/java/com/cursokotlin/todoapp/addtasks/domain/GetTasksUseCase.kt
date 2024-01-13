package com.cursokotlin.todoapp.addtasks.domain

import com.cursokotlin.todoapp.addtasks.data.TaskRepository
import com.cursokotlin.todoapp.addtasks.ui.model.TasksModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para conectarnos
 *
 * @property taskRepository
 */
class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TasksModel>> {
        return taskRepository.tasks
    }


}