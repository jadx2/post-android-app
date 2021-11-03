package com.example.postapp.posts

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.postapp.FetchStatus
import com.example.postapp.R
import com.example.postapp.database.poststable.Post

/***
 * Binders for the views:
 * - Sets the post's title
 * - Sets the post's body
 * - Manages the visibility of the loading and error images
 */
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
