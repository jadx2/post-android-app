package com.example.postapp.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postapp.network.PostsApi
import com.example.postapp.network.PostsProperty
import retrofit2.Call
import retrofit2.Response

class PostsViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response:  LiveData<String>
        get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        PostsApi.retrofitService.getPosts().enqueue(object: retrofit2.Callback<List<PostsProperty>> {
            override fun onResponse(call: Call<List<PostsProperty>>, response: Response<List<PostsProperty>>) {
                _response.value = "Success: ${response.body()?.size} posts retrieved"
            }

            override fun onFailure(call: Call<List<PostsProperty>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

        })
        _response.value = "Set the posts response here!"
    }
}