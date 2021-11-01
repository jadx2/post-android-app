package com.example.postapp.database.poststable

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PostsTableDao {

    @Insert
    fun insert(post: Post)

    @Update
    fun update(post: Post)

    @Query("SELECT * from posts_table ORDER BY id DESC")
    fun getAllPosts(): LiveData<List<Post>>
}