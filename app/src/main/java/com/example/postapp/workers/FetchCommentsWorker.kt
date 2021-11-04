package com.example.postapp.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.postapp.comments.KEY_POST_ID
import com.example.postapp.database.commentstable.Comment
import com.example.postapp.database.commentstable.CommentsDao
import com.example.postapp.network.PostsApi
import java.lang.Exception

class FetchCommentsWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val postId = inputData.getString(KEY_POST_ID)?.toInt()
        return try {
            val listResult = PostsApi.retrofitService.getComments(postId)
            Log.wtf("Jaa", postId.toString())
            Result.success()
//            listResult.forEach {
//                val comment = Comment(it.id, it.postId, it.name, it.email, it.body)
//                CommentsDao.insert(comment)
//            }
        } catch (e: Exception) {
            Log.wtf("Jaa", "Failure: ${e.message}")
            Result.failure()
        }
    }
}