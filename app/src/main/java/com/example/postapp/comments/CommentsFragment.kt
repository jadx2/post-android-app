package com.example.postapp.comments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
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
//        val dataSource = PostsDatabase.getInstance(application).commentsDao
        val viewModelFactory = CommentsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommentsViewModel::class.java]
        binding.lifecycleOwner = this
        binding.commentsViewModel = viewModel
        val adapter = CommentsAdapter()
        binding.commentsList.adapter = adapter
//        viewModel.comments
//            .observe(viewLifecycleOwner, Observer {
//                it?.let {
//                    adapter.submitList(it)
//                }
//            })

        return binding.root
    }

    /***
     * The postId that is passed from the post's click handler is used in the onFetch to retrieve
     * all the comment's for that post
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: CommentsFragmentArgs by navArgs()
        viewModel.fetchComments(args.postSelected)
    }
}