package com.proyecto.tasks.addtasks.data.di

import android.content.Context
import androidx.room.Room
import com.proyecto.tasks.addtasks.data.DataBase
import com.proyecto.tasks.addtasks.data.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    fun provideTasksDao(DataBase: DataBase): TaskDao {
        return DataBase.taskDao()
    }
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context):DataBase{
        return Room.databaseBuilder(appContext,DataBase::class.java,"TaskDataBase").build()
    }
}