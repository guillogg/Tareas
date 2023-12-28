package com.proyecto.tasks.addtasks.domain

import com.proyecto.tasks.addtasks.data.TaskReposity
import com.proyecto.tasks.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskReposity: TaskReposity
) {
    operator fun invoke(): Flow<List<TaskModel>>{
        return taskReposity.tasks
    }
}