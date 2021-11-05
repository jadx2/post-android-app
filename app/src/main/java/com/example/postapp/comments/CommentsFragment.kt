package com.example.postapp.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.work.WorkInfo
import androidx.work.hasKeyWithValueOfType
import com.example.postapp.R
import com.example.postapp.databinding.FragmentCommentsBinding

class CommentsFragment : Fragment() {

    lateinit var binding: FragmentCommentsBinding
    private lateinit var viewModel: CommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_comments, container, false)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = CommentsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommentsViewModel::class.java]
        binding.lifecycleOwner = this
        binding.commentsViewModel = viewModel
        val adapter = CommentsAdapter()
        binding.commentsList.adapter = adapter
        val args: CommentsFragmentArgs by navArgs()
        viewModel.fetchComments(args.postSelected)
        viewModel.outputWorkInfo.observe(viewLifecycleOwner, {
            if (it.state == WorkInfo.State.SUCCEEDED && it.outputData.hasKeyWithValueOfType<String>(
                    "comments"
                )
            ) {
                val jsonComments = it.outputData.getString("comments")
                val comments = jsonAdapter.fromJson(jsonComments)?.list!!
                viewModel.setComments(comments)
            }
        })
        viewModel.comments
            .observe(viewLifecycleOwner,  {
                it?.let {
                    adapter.submitList(it)
                }
            })

        return binding.root
    }
}