package com.example.issue_tracker.ui.milestone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentMileStoneBinding
import com.example.issue_tracker.domain.model.MileStone


class MileStoneFragment : Fragment() {

    private lateinit var binding:FragmentMileStoneBinding
    private lateinit var adapter: MilestoneAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_mile_stone, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= MilestoneAdapter()
        binding.rvMilestone.adapter= adapter
        adapter.submitList(makeDummyMileStones())

    }


    private fun makeDummyMileStones(): MutableList<MileStone> {
        val milestones = mutableListOf<MileStone>()
        for(i in 0..10){
            milestones.add(MileStone(i,"마일스톤 제목${i}", "마일스톤 더미 콘테츠", "06-14", 2,2))
        }
        return milestones
    }
}