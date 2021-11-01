package com.example.postapp.database.poststable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class Post(
    @PrimaryKey
    val id: Long? ,
    @ColumnInfo(name = "user")
    val userId: Long?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "body")
    val body: String?
)
