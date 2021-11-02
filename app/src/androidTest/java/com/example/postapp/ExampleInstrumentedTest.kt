//package com.example.postapp
//
//import android.content.Context
//import androidx.room.Room.inMemoryDatabaseBuilder
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.postapp.database.PostsDatabase
//import com.example.postapp.database.poststable.Post
//import com.example.postapp.database.poststable.PostsTableDao
//import okio.IOException
//import org.junit.After
//
//import org.junit.Test
//import org.junit.runner.RunWith
//
//import org.junit.Assert.*
//import org.junit.Before
//
//@RunWith(AndroidJUnit4::class)
//class SimpleEntityReadWriteTest {
//    private lateinit var postsTableDao: PostsTableDao
//    private lateinit var db: PostsDatabase
//
//    @Before
//    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = inMemoryDatabaseBuilder(
//            context, PostsDatabase::class.java)
//            .allowMainThreadQueries()
//            .build()
//        postsTableDao = postsTableDao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertPost() {
//        val post = Post(1, 1, "Hello", "Lorem Ipsum asdasdasdasdas" )
//        postsTableDao.insert(post)
//        val allPosts = postsTableDao.getAllPosts()
//        assertEquals(
//            allPosts[0].title,
//            "Hello"
//        )
//    }
//}