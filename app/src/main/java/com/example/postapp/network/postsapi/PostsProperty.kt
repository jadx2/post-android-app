package com.example.postapp.network.postsapi

import com.squareup.moshi.Json

data class PostsProperty(
    @Json(name = "id")
    val id: Int,
    @Json(name ="userId")
    val userId: Int,
    @Json(name ="title")
    val title: String,
    @Json(name ="body")
    val body: String
)
