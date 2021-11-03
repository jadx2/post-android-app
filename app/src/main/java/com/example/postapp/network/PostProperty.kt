package com.example.postapp.network

import com.squareup.moshi.Json

data class PostProperty(
    @Json(name = "id")
    val id: Int,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "body")
    val body: String
)
