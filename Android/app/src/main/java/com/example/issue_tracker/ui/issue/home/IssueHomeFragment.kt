package com.example.issue_tracker.ui.issue.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper

import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentIssueHomeBinding


class IssueHomeFragment : Fragment() {

    private lateinit var binding: FragmentIssueHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_issue_home, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempList = listOf(
            Issue("마일스톤", "제목", "설명", "label"),
            Issue("마스터즈 코스1", "이슈트래커1", "6월 13일에서 20일까지", "ABCDEF"),
            Issue("마스터즈 코스2", "이슈트래커2", "7월 9일에서 12일까지", "asdfef")
        )

        val adapter = IssueAdapter()
        binding.rvIssue.adapter = adapter
        adapter.submitList(tempList)

        val swipeHelperCallback = ItemHelper(adapter).apply {
            // 스와이프한 뒤 고정시킬 위치 지정
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)    // 1080 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvIssue)
    }

}