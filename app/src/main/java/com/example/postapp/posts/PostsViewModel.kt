package com.example.postapp.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostsViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response:  LiveData<String>
        get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        _response.value = "Set the posts response here!"
    }
}