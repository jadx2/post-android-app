package com.example.postapp.database.poststable

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface PostsDao {

    @Insert(onConflict = REPLACE)
    fun insert(post: Post)

    @Query("SELECT * from posts_table")
    fun getAllPosts(): List<Post>
}