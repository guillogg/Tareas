package com.proyecto.tasks.addtasks.domain

import com.proyecto.tasks.addtasks.data.TaskReposity
import com.proyecto.tasks.addtasks.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskReposity: TaskReposity) {
    suspend operator fun invoke(taskModel: TaskModel){
        taskReposity.delete(taskModel)
    }
}