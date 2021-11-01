package com.example.postapp.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postapp.network.postsapi.PostsApi
import kotlinx.coroutines.*

class PostsViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response:  LiveData<String>
        get() = _response

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getPosts()
    }

    private fun getPosts() {
        coroutineScope.launch {
            _response.value = result()
        }
    }

    private suspend fun result(): String {
        val listResult = PostsApi.retrofitService.getPosts()
        return withContext(Dispatchers.IO) {
            try {
                "Success: ${listResult.size} Posts retrieved"
            } catch (t: Throwable) {
                "Failure: " + t.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}