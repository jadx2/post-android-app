package com.example.postapp.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.postapp.R
import com.example.postapp.database.PostsDatabase
import com.example.postapp.databinding.FragmentCommentsBinding
import com.example.postapp.posts.PostsViewModel
import com.example.postapp.posts.PostsViewModelFactory

class CommentsFragment : Fragment() {

    lateinit var binding: FragmentCommentsBinding
    lateinit var viewModel: CommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_comments, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PostsDatabase.getInstance(application).commentsDao
        val viewModelFactory = CommentsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommentsViewModel::class.java]
        binding.lifecycleOwner = this
        binding.commentsViewModel = viewModel

        return binding.root
    }
}