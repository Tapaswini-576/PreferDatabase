package com.example.preferdatabase.ui



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.preferdatabase.data.TaskRepository
import com.example.preferdatabase.data.local.AppDatabase
import com.example.preferdatabase.data.local.TaskEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repo: TaskRepository) : ViewModel() {

    val tasks = repo.tasks.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun add(title: String) = viewModelScope.launch { repo.add(title) }
    fun toggle(task: TaskEntity) = viewModelScope.launch { repo.toggle(task) }
    fun delete(task: TaskEntity) = viewModelScope.launch { repo.delete(task) }


    companion object {
        fun factory(context: android.content.Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val dao = AppDatabase.get(context).taskDo()
                    val repo = TaskRepository(dao)
                    return TaskViewModel(repo) as T
                }
            }
    }
}