package com.example.postapp.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.postapp.database.commentstable.CommentsDao
import com.example.postapp.posts.PostsViewModel
import java.lang.IllegalArgumentException

class CommentsViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheck_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            return CommentsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
