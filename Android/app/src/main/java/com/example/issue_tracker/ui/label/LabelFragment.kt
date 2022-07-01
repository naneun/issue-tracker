package com.example.issue_tracker.ui.label

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentLabelBinding
import com.example.issue_tracker.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LabelFragment : Fragment() {

    private lateinit var binding: FragmentLabelBinding
    private lateinit var adapter: LabelAdapter
    private lateinit var navigator: NavController
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_label, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LabelAdapter(
            switchEditMode = { switchToEditMode() },
            addCheckList = { addCheckList(it) },
            deleteCheckList = { deleteCheckList(it) },
            switchCheckBox = { switchCheckBox(it) }
        )

        navigator = Navigation.findNavController(view)
        binding.rvLabel.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                async { loadIssueList() }
            }
        }
        moveToMakeLabel()
        closeToEditMode()
        removeLabel()
    }

    private suspend fun loadIssueList() {
        viewModel.labelList.collect {
            adapter.submitList(it)
        }
    }

    private fun moveToMakeLabel() {
        binding.iBtnLabelAdd.setOnClickListener {
            navigator.navigate(R.id.action_navigation_label_to_labelWriteFragment)
        }
    }

    private fun switchToEditMode() {
        viewModel.clearCheckList()
        viewModel.labelEditModeOn()
        binding.clLabelToolbarOrigin.visibility = View.GONE
        binding.clLabelToolbarEdit.visibility = View.VISIBLE
        setSelectedIssueCount()
        adapter.notifyDataSetChanged()
    }

    private fun closeToEditMode() {
        binding.btnLabelEditClose.setOnClickListener {
            viewModel.labelEditModeOff()
            binding.clLabelToolbarOrigin.visibility = View.VISIBLE
            binding.clLabelToolbarEdit.visibility = View.GONE
            adapter.notifyDataSetChanged()
        }
    }

    private fun addCheckList(itemId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addCheckList(itemId)
            viewModel.labelEditModeOn()
            setSelectedIssueCount()
        }
    }

    private fun deleteCheckList(itemId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.removeCheckList(itemId)
            viewModel.labelEditModeOff()
            setSelectedIssueCount()
        }
    }

    private fun switchCheckBox(view: CheckBox) {
        if (viewModel.labelEditMode.value) {
            view.visibility = View.VISIBLE
            view.isChecked = false
        } else {
            view.visibility = View.GONE
            view.isChecked = false
        }
    }

    private fun setSelectedIssueCount() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editCheckList.collect {
                    binding.tvLabelCount.text = it.size.toString()
                }
            }
        }
    }

    private fun removeLabel() {
        binding.btnLabelRemove.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.removeLabelList()
            }
            binding.clLabelToolbarOrigin.visibility = View.VISIBLE
            binding.clLabelToolbarEdit.visibility = View.GONE
            adapter.notifyDataSetChanged()
            Log.d("TEST", "${viewModel.labelEditMode.value}")
            Log.d("TEST", "${viewModel.labelList.value.size}")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLabelList()
    }
}