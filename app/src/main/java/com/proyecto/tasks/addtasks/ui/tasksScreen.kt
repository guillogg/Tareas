package com.proyecto.tasks.addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.proyecto.tasks.addtasks.ui.model.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {

    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(false)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<TaskUIState>(
        initialValue = TaskUIState.Loading,
        key1 = lifecycle,
        key2 = tasksViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            tasksViewModel.uiState.collect { value = it }
        }

    }
    when (uiState) {
        is TaskUIState.Error -> {}
        TaskUIState.Loading -> {
            CircularProgressIndicator()
        }

        is TaskUIState.Succes -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AddTasksDialog(
                    showDialog,
                    onDismiss = { tasksViewModel.onDialogClose() },
                    onTaskAdded = { tasksViewModel.onTaskCreated(it) })
                FabDialog(Modifier.align(Alignment.BottomEnd), tasksViewModel)
                TaskList((uiState as TaskUIState.Succes).tasks,tasksViewModel)
            }
        }
    }

}

@Composable
fun TaskList(tasks: List<TaskModel>,tasksViewModel: TasksViewModel) {

    LazyColumn {
        items(tasks, key = { it.id }) {task->
           ItemTask(task,tasksViewModel)
        }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, tasksViewModel: TasksViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 7.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    tasksViewModel.onItemRemove(taskModel)
                })
            }

    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { tasksViewModel.onCheckBoxSelected(taskModel) })
        }
    }

}

@Composable
fun FabDialog(modifier: Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(onClick = {
        tasksViewModel.onShowDialogClicked()
    }, modifier = modifier.padding(16.dp)) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(14.dp)
            ) {
                Text(
                    text = "Añade una Tarea nueva",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(14.dp))
                TextField(value = myTask, onValueChange = { myTask = it }, singleLine = true)
                Spacer(modifier = Modifier.size(14.dp))
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir Tarea")
                }
            }
        }
    }
}
