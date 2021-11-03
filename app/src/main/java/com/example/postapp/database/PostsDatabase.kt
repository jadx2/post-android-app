package com.example.postapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.postapp.database.commentstable.Comment
import com.example.postapp.database.commentstable.CommentsDao
import com.example.postapp.database.poststable.Post
import com.example.postapp.database.poststable.PostsDao

/***
 * Creates the instance of the table if there is no instance created.
 */
@Database(entities = [Post::class, Comment::class], version = 4, exportSchema = false)
abstract class PostsDatabase: RoomDatabase() {
    abstract val postsDao: PostsDao
    abstract val commentsDao: CommentsDao

    companion object {
        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getInstance(context: Context): PostsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostsDatabase::class.java,
                        "posts_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}