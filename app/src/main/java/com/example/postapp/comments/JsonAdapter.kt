package com.example.postapp.comments

import com.example.postapp.workers.ToListConvertor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
val jsonAdapter = moshi.adapter(ToListConvertor::class.java)
