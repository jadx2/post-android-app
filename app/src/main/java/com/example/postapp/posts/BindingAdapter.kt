package com.example.postapp.posts

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.postapp.database.poststable.Post

@BindingAdapter("postTitle")
fun TextView.setPostTitle(item: Post?) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("postBody")
fun TextView.setPostBody(item: Post?) {
    item?.let {
        text = item.body
    }
}
