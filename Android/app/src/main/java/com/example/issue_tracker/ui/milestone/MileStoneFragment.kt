package com.example.issue_tracker.ui.milestone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentMileStoneBinding
import com.example.issue_tracker.domain.model.MileStone
import com.example.issue_tracker.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MileStoneFragment : Fragment() {

    private lateinit var binding: FragmentMileStoneBinding
    private lateinit var adapter: MilestoneAdapter
    private lateinit var navigator: NavController
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mile_stone, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMileStoneList()
        adapter = MilestoneAdapter()
        navigator = Navigation.findNavController(view)
        binding.iBtnMilestoneAdd.setOnClickListener {
            moveToAddMileStone()
        }
        binding.rvMilestone.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                async { loadMilestoneList() }
            }
        }
    }

    private fun moveToAddMileStone() {
        navigator.navigate(R.id.action_navigation_milestone_to_mileStoneWriteFragment)
    }

    private suspend fun loadMilestoneList() {
        viewModel.mileStoneList.collect {
            adapter.submitList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMileStoneList()
    }
}