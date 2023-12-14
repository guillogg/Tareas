package com.proyecto.tasks.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proyecto.tasks.addtasks.ui.model.TaskModel
import javax.inject.Inject

class TasksViewModel@Inject constructor():ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog :LiveData<Boolean> = _showDialog

    private val _task = mutableStateListOf<TaskModel>()
    val task:List<TaskModel> = _task
    fun onDialogClose(){
        _showDialog.value= false
    }

    fun onTaskCreated(task: String) {
        onDialogClose()
        _task.add(TaskModel(task = task))
    }

    fun onShowDialogClicked() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _task.indexOf(taskModel)
        _task[index] = _task[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        val task = _task.find { it.id == taskModel.id }
        _task.remove(task)
    }
}