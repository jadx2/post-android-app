package com.example.postapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.postapp.database.poststable.Post
import com.example.postapp.database.poststable.PostsTableDao

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class PostsDatabase: RoomDatabase() {
    abstract val postsTableDao: PostsTableDao

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