package com.example.issue_tracker.ui.issue.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.R
import com.example.issue_tracker.common.Constants
import com.example.issue_tracker.databinding.FragmentIssueDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IssueDetailFragment : Fragment() {
    private lateinit var binding: FragmentIssueDetailBinding
    private val viewModel: IssueDetailViewModel by viewModels()
    private lateinit var adapter: IssueDetailAdapter
    private var issueID = 0
    private var page = 0
    private lateinit var navigator:NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = Navigation.findNavController(view)
        issueID = requireArguments().getInt(Constants.ISSUE_ID_KEY)
        adapter = IssueDetailAdapter()
        binding.id = issueID.toString()
        binding.rvIssueDetail.adapter = adapter
        binding.writer = "SomeOne"
        binding.time = "2022-07-01T02:33:15"
        viewModel.loadDetail(issueID)
        pageUpdate()
        loadDetail()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {  loadComments()}
            }
        }

        switchEditMode()
        binding.rvIssueDetail.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    adapter.deleteLoading()
                }
            }
        })
    }

    private fun pageUpdate(){
        viewModel.loadComments(issueID, page++)
    }

    private fun loadDetail(){
        viewModel.issueDetail.observe(viewLifecycleOwner){
            binding.tvIssueDetailTitle.text = it.title
            binding.writer = it.writer.name?:"SomeOne"
            binding.time = it.time.toString()
            if (it.state) {
                binding.btnIssueDetailOpenState.isVisible = true
                binding.btnIssueDetailCloseState.isVisible = false
            } else {
                binding.btnIssueDetailOpenState.isVisible = false
                binding.btnIssueDetailCloseState.isVisible = true
            }
        }
    }



    private suspend fun loadComments(){
        viewModel.comments.collect{
            if(it.isNotEmpty()) {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
       }
    }

    private fun switchEditMode(){
        binding.iBtnIssueDetailMore.setOnClickListener {
            navigator.navigate(R.id.action_issueDetailFragment_to_issueDetailMoreDialogFragment)
        }
    }

}