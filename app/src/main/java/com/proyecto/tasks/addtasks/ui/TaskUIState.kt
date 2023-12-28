package com.proyecto.tasks.addtasks.ui

import com.proyecto.tasks.addtasks.ui.model.TaskModel

sealed interface TaskUIState {
    object Loading:TaskUIState
    data class Error(val throwable: Throwable):TaskUIState
    data class Succes (val tasks:List<TaskModel>):TaskUIState
}