package com.example.postapp.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postapp.network.PostsApi
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PostsViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response:  LiveData<String>
        get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        PostsApi.retrofitService.getPosts().enqueue(object: retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

        })
        _response.value = "Set the posts response here!"
    }
}