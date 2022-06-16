package com.example.issue_tracker.ui.issue.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.fragment.app.Fragment
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentIssueHomeBinding
import com.example.issue_tracker.domain.model.Issue


class IssueHomeFragment : Fragment() {

    private lateinit var binding: FragmentIssueHomeBinding
    private lateinit var navigator:NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator= Navigation.findNavController(view)
        val tempList = listOf<Issue>(
            Issue(1, "마일스톤", "제목", "설명", "label"),
            Issue(2, "마스터즈 코스1", "이슈트래커1", "6월 13일에서 20일까지", "ABCDEF"),
            Issue(3, "마스터즈 코스2", "이슈트래커2", "7월 9일에서 12일까지", "asdfef")
        )

        binding.btnFilter.setOnClickListener {
            navigator.navigate(R.id.action_navigation_issue_to_issueWriteFragment)

        binding.rvIssue.apply {
            this.adapter = IssueAdapter().apply {
                this.submitList(tempList)
            }

        }
    }
}