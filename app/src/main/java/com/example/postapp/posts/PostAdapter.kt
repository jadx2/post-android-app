package com.example.postapp.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.postapp.database.poststable.Post
import com.example.postapp.databinding.PostsListItemBinding
import com.example.postapp.posts.PostsAdapter.ViewHolder

class PostsAdapter: ListAdapter<Post, ViewHolder>(PostsDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.postTitle.text = item.title.toString()
        holder.postBody.text = item.body.toString()
    }


    class ViewHolder private constructor(val binding: PostsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val postTitle: TextView = binding.postTitle
        val postBody: TextView = binding.postBody

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostsListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PostsDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}