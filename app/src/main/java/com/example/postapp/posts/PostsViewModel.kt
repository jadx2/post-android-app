package com.example.postapp.posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.postapp.FetchStatus
import com.example.postapp.database.poststable.Post
import com.example.postapp.database.poststable.PostsDao
import com.example.postapp.network.PostsApi
import kotlinx.coroutines.*
import java.lang.Exception

/***
 * ViewModel for the posts
 */
class PostsViewModel(
    private val database: PostsDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val _status = MutableLiveData<FetchStatus>()
    val status: LiveData<FetchStatus>
        get() = _status
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _navigateToPostComments = MutableLiveData<Int?>()
    val navigateToPostComments: LiveData<Int?>
        get() = _navigateToPostComments

    /***
     * Fetches the posts from the API, inserts them in the database and returns them. Handles the status of the query.
     */
    fun onFetch() {
        viewModelScope.launch {
            _status.postValue(FetchStatus.LOADING)
            withContext(Dispatchers.IO) {
                try {
                    val listResult = PostsApi.retrofitService.getPosts()
                    listResult.forEach {
                        val post = Post(it.id, it.userId, it.title, it.body)
                        database.insert(post)
                    }
                    val fetchPosts = database.getAllPosts()
                    _posts.postValue(fetchPosts)
                    _status.postValue(FetchStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(FetchStatus.ERROR)
                    _posts.postValue(ArrayList())
                }
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