package com.example.postapp.comments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.example.postapp.workers.FetchCommentsFromApiWorker
import com.example.postapp.workers.FetchCommentsFromDatabaseWorker
import com.example.postapp.workers.SaveCommentsToDatabaseWorker
import org.w3c.dom.Comment
import java.util.*

/***
 * ViewModel for comments
 */
class CommentsViewModel(
    application: Application
) : AndroidViewModel(application) {

    lateinit var outputWorkInfo: LiveData<WorkInfo>

    private val _comments = MutableLiveData<List<com.example.postapp.database.commentstable.Comment>>()
    val comments: LiveData<List<com.example.postapp.database.commentstable.Comment>>
        get() = _comments

    private fun createInputDataDataBaseAndPostId(postId: Int): Data {
        val builder = Data.Builder()
        postId.let {
            builder.putString(KEY_POST_ID, postId.toString())
        }
        return builder.build()
    }

    private val workManager = WorkManager.getInstance(application)

    fun fetchComments(postId: Int) {
        val fetchRequest = OneTimeWorkRequestBuilder<FetchCommentsFromApiWorker>()
            .setInputData(createInputDataDataBaseAndPostId(postId))
            .build()

        val insertToDatabase =
            OneTimeWorkRequestBuilder<SaveCommentsToDatabaseWorker>().build()

        val fetchComments =
            OneTimeWorkRequestBuilder<FetchCommentsFromDatabaseWorker>()
                .build()

        workManager.beginWith(fetchRequest).then(insertToDatabase).then(fetchComments).enqueue()
        outputWorkInfo = workManager.getWorkInfoByIdLiveData(fetchComments.id)
    }

    fun setComments(comments: List<com.example.postapp.database.commentstable.Comment>) {
        _comments.value = comments
    }
}
