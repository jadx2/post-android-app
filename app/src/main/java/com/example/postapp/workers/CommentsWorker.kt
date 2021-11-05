package com.example.postapp.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.postapp.comments.KEY_POST_ID
import com.example.postapp.comments.jsonAdapter
import com.example.postapp.database.PostsDatabase
import com.example.postapp.database.commentstable.Comment
import com.example.postapp.network.CommentProperty
import com.example.postapp.network.PostsApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

lateinit var fetchedComments: List<CommentProperty>
private var postId = 0

class FetchCommentsFromApiWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        postId = inputData.getString(KEY_POST_ID)?.toInt()!!
        return try {
            PostsApi.retrofitService.getComments(postId)
                .enqueue(object : Callback<List<CommentProperty>> {
                    override fun onResponse(
                        call: Call<List<CommentProperty>>,
                        response: Response<List<CommentProperty>>
                    ) {
                        fetchedComments = response.body()!!
                    }

                    override fun onFailure(call: Call<List<CommentProperty>>, t: Throwable) {
                    }
                })
            Result.success()
        } catch (e: Exception) {
            Log.wtf("Jaim", "Failure FCFA: ${e.message}")
            Result.failure()
        }
    }
}

class SaveCommentsToDatabaseWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    private val database = PostsDatabase.getInstance(applicationContext).commentsDao
    override fun doWork(): Result {
        return try {
            fetchedComments.forEach {
                val comment = Comment(it.id, it.postId, it.name, it.email, it.body)
                database.insert(comment)
            }
            Result.success()
        } catch (e: Exception) {
            "Failure: " + e.message
            Log.wtf("JaimFailed", "Failure SCTDB: ${e.message}")
            Result.failure()
        }
    }
}

class FetchCommentsFromDatabaseWorker(ctx: Context, params: WorkerParameters) :
    Worker(ctx, params) {
    private val database = PostsDatabase.getInstance(applicationContext).commentsDao

    override fun doWork(): Result {
        val fetchComments = database.getCommentsByPostId(postId)
        val listOfComments = ToListConvertor(fetchComments)
        val strPackage = workDataOf("comments" to jsonAdapter.toJson(listOfComments))
        return try {
            Result.success(strPackage)
        } catch (e: Exception) {
            Log.wtf("Jaim", "Failure: ${e.message}")
            Result.failure()
        }
    }
}

data class ToListConvertor(val list: List<Comment>)