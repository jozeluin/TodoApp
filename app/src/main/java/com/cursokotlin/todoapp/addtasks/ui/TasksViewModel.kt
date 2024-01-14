package com.cursokotlin.todoapp.addtasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.todoapp.addtasks.domain.AddTaskUseCase
import com.cursokotlin.todoapp.addtasks.domain.DeleteTaskUseCae
import com.cursokotlin.todoapp.addtasks.domain.GetTasksUseCase
import com.cursokotlin.todoapp.addtasks.domain.UpdateTaskUseCase
import com.cursokotlin.todoapp.addtasks.ui.TasksUiState.Success
import com.cursokotlin.todoapp.addtasks.ui.model.TasksModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTasksUiState: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCae: DeleteTaskUseCae,
    getTasksUseCase: GetTasksUseCase


) : ViewModel() {
    //Decimos que por cada elemento del StateFlow (nos metemos en Success) lo transformamos en taskModel
    val uiState: StateFlow<TasksUiState> = getTasksUseCase().map(::Success)
        .catch { TasksUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TasksUiState.Loading)

    //stateIn, convierte un flow en un stateFlow,recibe un viewMoldScope
    //SharingStarted.WhileSubscribed( 5000), cuando la pantalla este en segundo plano cancela en flow si almenos hemos estado 5s
    //TasksUiState.Loading, despues le decimos que el estado inicial es Loading
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

//Para borrar ya no se uiliza esto, hay que hacerlo desde la base de datos
    //private val _tasks = mutableStateListOf<TasksModel>()
    //val tasks: List<TasksModel> = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false
        // ya no se usa//_tasks.add(TasksModel(task = task))

        viewModelScope.launch {
            addTasksUiState(TasksModel(task = task))
        }


    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(tasksModel: TasksModel) {
        //Actualizar check

        //    val index = _tasks.indexOf(tasksModel)
        //    _tasks[index] = _tasks[index].let {
        //       it.copy(selected = !it.selected)
        // }
        viewModelScope.launch {
            updateTaskUseCase(tasksModel.copy(selected = !tasksModel.selected))
        }

    }

    fun OnItemRemove(tasksModel: TasksModel) {

        //     val task = _tasks.find { it.id == tasksModel.id }
        //     _tasks.remove(task)

        viewModelScope.launch {
            deleteTaskUseCae(tasksModel)
        }

    }
}