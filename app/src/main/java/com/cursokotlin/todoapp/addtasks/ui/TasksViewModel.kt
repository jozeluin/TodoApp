package com.cursokotlin.todoapp.addtasks.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TasksViewModel @Inject constructor() :ViewModel() {

    private val _showDialog=MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> =_showDialog

    fun onDialogClose() {
       _showDialog.value=false
    }

    fun onTaskCreated(task: String) {

        Log.i("JoseLuis",task)
        _showDialog.value=false
    }

    fun onShowDialogClick() {
        _showDialog.value=true
    }
}