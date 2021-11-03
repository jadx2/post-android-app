package com.example.postapp.comments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.postapp.FetchStatus
import com.example.postapp.R
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

@BindingAdapter("fetchStatus")
fun bindStatus(statusImageView: ImageView, status: FetchStatus?) {
    when (status) {
        FetchStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        FetchStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.error)
        }
        FetchStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
