package com.example.postapp.comments

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.postapp.database.commentstable.Comment

@BindingAdapter("commentBody")
fun TextView.setCommentBody(item: Comment?) {
    item?.let {
        text = item.body
    }
}

@BindingAdapter("commentAuthor")
fun TextView.setCommentAuthor(item: Comment?) {
    item?.let {
        text = item.name
    }
}