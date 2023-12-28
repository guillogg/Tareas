package com.proyecto.tasks.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.tasks.addtasks.domain.AddTaskUseCase
import com.proyecto.tasks.addtasks.domain.DeleteTaskUseCase
import com.proyecto.tasks.addtasks.domain.GetTasksUseCase
import com.proyecto.tasks.addtasks.domain.UpdateTaskUseCase
import com.proyecto.tasks.addtasks.ui.TaskUIState.Succes
import com.proyecto.tasks.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel@Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTaskUseCase: GetTasksUseCase

):ViewModel() {

    val uiState:StateFlow<TaskUIState> = getTaskUseCase().map  (::Succes )
        .catch {TaskUIState.Error(it)}
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),TaskUIState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog :LiveData<Boolean> = _showDialog

    //private val _task = mutableStateListOf<TaskModel>()
    //val task:List<TaskModel> = _task
    fun onDialogClose(){
        _showDialog.value= false
    }

    fun onTaskCreated(task: String) {
        onDialogClose()


        viewModelScope.launch {
            addTaskUseCase(TaskModel(task=task))
        }
    }

    fun onShowDialogClicked() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {


        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
      //  val task = _task.find { it.id == taskModel.id }
       // _task.remove(task)
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }

    }
}