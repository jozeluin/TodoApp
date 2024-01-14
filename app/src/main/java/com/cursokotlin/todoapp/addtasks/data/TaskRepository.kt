package com.cursokotlin.todoapp.addtasks.data

import com.cursokotlin.todoapp.addtasks.ui.model.TasksModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Es la puerta de entrada de la capa de "data"
 */
@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TasksModel>> =
        taskDao.getTasks().map { items -> items.map { TasksModel(it.id, it.task, it.selected) } }

    suspend fun add(tasksModel: TasksModel) {
        taskDao.addTask(TaskEntity(tasksModel.id, tasksModel.task, tasksModel.selected))
    }

    suspend fun update(taskModel:TasksModel){
        taskDao.updateTask(TaskEntity(taskModel.id,taskModel.task,taskModel.selected))
    }
    suspend fun delete(taskModel:TasksModel){
        taskDao.deleteTask(taskModel.toData())//Es lo mismo de arriba
    }


}


fun TasksModel.toData():TaskEntity{
    return TaskEntity(this.id,this.task,this.selected)

}