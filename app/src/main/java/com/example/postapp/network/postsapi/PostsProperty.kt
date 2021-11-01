package com.example.postapp.network.postsapi

data class PostsProperty(
    val userId: Long,
    val id: Int,
    val title: String,
    val body: String
)
