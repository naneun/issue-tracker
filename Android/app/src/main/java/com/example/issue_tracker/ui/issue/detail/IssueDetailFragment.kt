package com.example.issue_tracker.ui.issue.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentIssueDetailBinding

class IssueDetailFragment : Fragment() {
    private lateinit var binding: FragmentIssueDetailBinding
    private val viewModel: IssueDetailViewModel by viewModels()
    private lateinit var adapter: IssueDetailAdapter
    private var issueID = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issueID = requireArguments().getInt("IssueID")
        adapter = IssueDetailAdapter()
        binding.rvIssueDetail.adapter = adapter
        viewModel.loadDetail(issueID)
        viewModel.issueDetail.observe(viewLifecycleOwner){
            binding.tvIssueDetailTitle.text = it.title
            binding.writer = it.writer.name
            binding.time = it.time.toString()
            if(it.state){
                binding.btnIssueDetailOpenState.isVisible=true
                binding.btnIssueDetailCloseState.isVisible=false
            }
            else{
                binding.btnIssueDetailOpenState.isVisible=false
                binding.btnIssueDetailCloseState.isVisible=true
            }
            adapter.submitList(it.comment)
        }
    }
}