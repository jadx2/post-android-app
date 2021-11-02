package com.example.postapp.posts

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.postapp.database.poststable.PostsDao
import java.lang.IllegalArgumentException

class PostsViewModelFactory(
    private val dataSource: PostsDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheck_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            return PostsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}