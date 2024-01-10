package com.cursokotlin.todoapp.addtasks.data

import com.cursokotlin.todoapp.addtasks.ui.model.TasksModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TasksModel>> =
        taskDao.getTasks().map { items -> items.map { TasksModel(it.id, it.task, it.selected) } }

    suspend fun add(tasksModel: TasksModel) {
        taskDao.addTask(TaskEntity(tasksModel.id, tasksModel.task, tasksModel.selected))
    }

}