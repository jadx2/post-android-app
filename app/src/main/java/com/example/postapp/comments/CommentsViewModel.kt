package com.example.postapp.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.postapp.database.commentstable.Comment
import com.example.postapp.database.commentstable.CommentsDao
import com.example.postapp.network.CommentProperty
import com.example.postapp.network.PostsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsViewModel(
    private val database: CommentsDao,
    application: Application
) : AndroidViewModel(application) {

    private val _selectedPost = MutableLiveData<String>()
    val selectedPost: LiveData<String>
        get() = _selectedPost

    init {
        getAllComments(1)
    }

    private fun getAllComments(postId: Int) {
        PostsApi.retrofitService.getComments(postId).enqueue(object : Callback<List<CommentProperty>> {
            override fun onResponse(call: Call<List<CommentProperty>>, response: Response<List<CommentProperty>>) {
                _selectedPost.value = "Success: ${response.body()?.size} comments"
            }

            override fun onFailure(call: Call<List<CommentProperty>>, t: Throwable) {
                _selectedPost.value = "Failure" + t.message
            }
        })
    }
}