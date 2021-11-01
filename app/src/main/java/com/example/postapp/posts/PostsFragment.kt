package com.example.postapp.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.postapp.R
import com.example.postapp.databinding.FragmentPostsBinding

class PostsFragment : Fragment() {
    lateinit var viewModel: PostsViewModel
    lateinit var binding: FragmentPostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)
        viewModel = ViewModelProvider(this)[PostsViewModel::class.java]
        binding.postsViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}