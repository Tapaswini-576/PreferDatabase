package com.example.preferdatabase.data


import com.example.preferdatabase.data.local.TaskDao
import com.example.preferdatabase.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {

    val tasks: Flow<List<TaskEntity>> = dao.observeAll()

    suspend fun add(title: String) =
        dao.insert(TaskEntity(title = title))

    suspend fun toggle(task: TaskEntity) =
        dao.update(task.copy(done = !task.done))

    suspend fun delete(task: TaskEntity) =
        dao.delete(task)
}
