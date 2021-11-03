package com.example.postapp.comments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.postapp.FetchStatus
import com.example.postapp.database.commentstable.Comment
import com.example.postapp.database.commentstable.CommentsDao
import com.example.postapp.database.poststable.Post
import com.example.postapp.network.CommentProperty
import com.example.postapp.network.PostsApi
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CommentsViewModel(
    private val database: CommentsDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val _status = MutableLiveData<FetchStatus>()
    val status: LiveData<FetchStatus>
        get() = _status
    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    fun onFetch(postId: Int) {
        viewModelScope.launch {
            _status.postValue(FetchStatus.LOADING)
            withContext(Dispatchers.IO) {
                try {
                    val listResult = PostsApi.retrofitService.getComments(postId)
                    listResult.forEach {
                        val comment = Comment(it.id, it.postId, it.name, it.email, it.body)
                        database.insert(comment)
                    }
                    val fetchComments = database.getCommentsByPostId(postId)
                    _comments.postValue(fetchComments)
                    _status.postValue(FetchStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(FetchStatus.ERROR)
                    _comments.postValue(ArrayList())
                }
            }
        }
    }
}