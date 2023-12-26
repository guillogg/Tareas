package com.proyecto.tasks.addtasks.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class] ,version = 1)
abstract class  DataBase:RoomDatabase(){
    abstract fun taskDao():TaskDao
}