package com.example.postapp.database.commentstable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CommentsDao {

    @Insert(onConflict = REPLACE)
    fun insert(comment: Comment)

    @Query("SELECT * from comments_table WHERE postId = :post_id")
    fun getCommentsByPostId(post_id: Int): List<Comment>
}