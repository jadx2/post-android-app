package com.example.postapp.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.postapp.FetchStatus
import com.example.postapp.database.commentstable.Comment
import com.example.postapp.database.commentstable.CommentsDao
import com.example.postapp.network.PostsApi
import com.example.postapp.workers.FetchCommentsWorker
import kotlinx.coroutines.*
import java.lang.Exception

/***
 * ViewModel for comments
 */
class CommentsViewModel(
//    private val database: CommentsDao,
    application: Application
) : AndroidViewModel(application) {

    private fun createInputDataDataBaseAndPostId(postId: Int): Data {
        val builder = Data.Builder()
        postId?.let {
            builder.putString(KEY_POST_ID, postId.toString())
        }
        return builder.build()
    }

    private val workManager = WorkManager.getInstance(application)

    fun fetchComments(postId: Int) {
        val fetchRequest = OneTimeWorkRequestBuilder<FetchCommentsWorker>()
            .setInputData(createInputDataDataBaseAndPostId(postId))
            .build()
        workManager.enqueue(fetchRequest)
    }

//    private var viewModelJob = Job()
//    private val _status = MutableLiveData<FetchStatus>()
//    val status: LiveData<FetchStatus>
//        get() = _status
//    private val _comments = MutableLiveData<List<Comment>>()
//    val comments: LiveData<List<Comment>>
//        get() = _comments
//
//    /***
//     * Fetches from the API, inserts the comments in the database and returns them. Handles the status of the query.
//     */
////    fun onFetch(postId: Int) {
//        viewModelScope.launch {
//            _status.postValue(FetchStatus.LOADING)
//            withContext(Dispatchers.IO) {
//                try {
//                    val listResult = PostsApi.retrofitService.getComments(postId)
//                    listResult.forEach {
//                        val comment = Comment(it.id, it.postId, it.name, it.email, it.body)
//                        database.insert(comment)
//                    }
//                    val fetchComments = database.getCommentsByPostId(postId)
//                    _comments.postValue(fetchComments)
//                    _status.postValue(FetchStatus.DONE)
//                } catch (e: Exception) {
//                    _status.postValue(FetchStatus.ERROR)
//                    _comments.postValue(ArrayList())
//                }
//            }
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }
}