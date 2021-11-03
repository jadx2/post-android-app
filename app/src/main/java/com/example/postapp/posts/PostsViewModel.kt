package com.example.postapp.posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.postapp.database.poststable.Post
import com.example.postapp.database.poststable.PostsDao
import com.example.postapp.network.PostProperty
import com.example.postapp.network.PostsApi
import kotlinx.coroutines.*

class PostsViewModel(
    private val database: PostsDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _navigateToPostComments = MutableLiveData<Int?>()
    val navigateToPostComments: LiveData<Int?>
        get() = _navigateToPostComments

    fun onFetch() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val listResult = PostsApi.retrofitService.getPosts()
                listResult.forEach {
                    val post = Post(it.id, it.userId, it.title, it.body)
                    database.insert(post)
                }
                val fetchPosts = database.getAllPosts()
                _posts.postValue(fetchPosts)
            }
        }
    }

    fun displayComments(post: Post) {
        _navigateToPostComments.value = post.id
    }

    fun displayCommentsComplete() {
        _navigateToPostComments.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}