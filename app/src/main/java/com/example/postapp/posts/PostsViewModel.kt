package com.example.postapp.posts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.postapp.database.poststable.Post
import com.example.postapp.database.poststable.PostsDao
import com.example.postapp.network.postsapi.PostsApi
import kotlinx.coroutines.*

class PostsViewModel(
    private val database: PostsDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val posts = MutableLiveData<List<Post>>()

    init {
    }

    fun onFetch() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val listResult = PostsApi.retrofitService.getPosts()
                listResult.forEach {
                    val post = Post(it.id, it.userId, it.title, it.body)
                    database.insert(post)
                }
                val fetchPosts = database.getAllPosts()
                posts.postValue(fetchPosts)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}