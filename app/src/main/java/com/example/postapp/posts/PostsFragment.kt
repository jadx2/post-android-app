package com.example.postapp.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.postapp.R
import com.example.postapp.database.PostsDatabase
import com.example.postapp.databinding.FragmentPostsBinding

class PostsFragment : Fragment() {
    lateinit var viewModel: PostsViewModel
    lateinit var binding: FragmentPostsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PostsDatabase.getInstance(application).postsDao
        val viewModelFactory = PostsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[PostsViewModel::class.java]
        binding.lifecycleOwner = this
        binding.postsViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onFetch()
    }
}