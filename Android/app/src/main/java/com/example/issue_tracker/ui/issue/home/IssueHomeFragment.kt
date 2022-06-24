package com.example.issue_tracker.ui.issue.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.issue_tracker.R
import com.example.issue_tracker.common.Constants
import com.example.issue_tracker.databinding.FragmentIssueHomeBinding
import com.example.issue_tracker.domain.model.Issue
import com.example.issue_tracker.domain.model.SpinnerType
import com.example.issue_tracker.ui.HomeViewModel
import kotlinx.coroutines.launch

class IssueHomeFragment : Fragment() {

    private lateinit var adapter: IssueAdapter
    private lateinit var binding: FragmentIssueHomeBinding
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private val viewModel: IssueHomeViewModel by viewModels()
    private lateinit var navigator: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = Navigation.findNavController(view)
        adapter = IssueAdapter { id ->
            moveToDetail(id)
        }
        binding.rvIssue.adapter = adapter

        val swipeHelperCallback = ItemHelper(adapter).apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)    // 1080 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvIssue)

        setUpIssueWriteBtn()
        setSwitchToFilterMode()
        setSwitchToListModeFromFilterMode()
        setSwitchSearchMode()
        setSwitchToListModeFromSearchMode()
        closeEditMode()
        removeIssue()
        closeIssueList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { loadStateList() }
                launch { loadLabelList() }
                launch { loadMileStoneList() }
                launch { loadUserList() }
                launch { loadIssueList() }
            }
        }

        binding.etlSearch.setEndIconOnClickListener {
            //To do: Search 로직 (백엔드 API와 협의 필요)
        }
    }

    private suspend fun loadIssueList() {
        viewModel.issueList.collect {
            adapter.submitList(it)
        }
    }

    private suspend fun loadStateList() {
        val stateList = mutableListOf<String>()
        viewModel.stateList.collect {
            it.forEach { state ->
                stateList.add(state.value)
            }
            setSpinner(stateList, SpinnerType.STATE)
        }

    }

    private suspend fun loadUserList() {
        val userList = mutableListOf(getString(R.string.spinner_default))
        sharedViewModel.userList.collect {
            it.forEach { user ->
                userList.add(user.name)
            }
            setSpinner(userList, SpinnerType.WRITER)
        }
    }

    private suspend fun loadLabelList() {
        val labelList = mutableListOf(getString(R.string.spinner_default))
        sharedViewModel.labelList.collect {
            it.forEach { label ->
                labelList.add(label.title)
            }
            setSpinner(labelList, SpinnerType.LABEL)
        }
    }

    private suspend fun loadMileStoneList() {
        val mileStoneList = mutableListOf(getString(R.string.spinner_default))
        sharedViewModel.mileStoneList.collect {
            it.forEach { mileStone ->
                mileStoneList.add(mileStone.title)
            }
            setSpinner(mileStoneList, SpinnerType.MILESTONE)
        }
    }

    private fun setSpinner(list: List<String>, type: SpinnerType) {
        val spinner = when (type) {
            SpinnerType.STATE -> binding.spinnerIssueState
            SpinnerType.LABEL -> binding.spinnerIssueLabel
            SpinnerType.MILESTONE -> binding.spinnerIssueMilestone
            else -> binding.spinnerIssueAssignee
        }
        val adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_spinner_filter,
                R.id.tv_filter_value,
                list
            )
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val text = view?.findViewById<TextView>(R.id.tv_filter_value)
                text?.setTextColor(Color.WHITE)
                view?.findViewById<View>(R.id.divider_filter_value)?.isVisible = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                view?.findViewById<View>(R.id.divider_filter_value)?.isVisible = true
            }
        }
    }

    private fun setSwitchSearchMode() {
        binding.btnSearch.setOnClickListener {
            binding.clSearch.isVisible = true
            binding.tbIssues.isVisible = false
            binding.btnPlusIssue.isVisible = false
            changeStatusBarSkyBLue()
        }
    }

    private fun setSwitchToListModeFromSearchMode() {
        binding.btnSearchClose.setOnClickListener {
            binding.clSearch.isVisible = false
            binding.tbIssues.isVisible = true
            binding.btnPlusIssue.isVisible = true
            changeStatusBarWhite()
        }
    }

    private fun setSwitchToFilterMode() {
        binding.btnFilter.setOnClickListener {
            binding.clIssueList.isVisible = false
            binding.clFilter.isVisible = true
            changeStatusBarSkyBLue()
        }
    }

    private fun setSwitchToListModeFromFilterMode() {
        binding.btnFilterClose.setOnClickListener {
            binding.clIssueList.isVisible = true
            binding.clFilter.isVisible = false
            changeStatusBarWhite()
        }
    }

    private fun setUpIssueWriteBtn() {
        binding.btnPlusIssue.setOnClickListener {
            navigator.navigate(R.id.action_navigation_issue_to_issueWriteFragment)
        }
        settingRecyclerview()
    }

    private fun settingRecyclerview() {
        adapter.issueAdapterEventListener = object : IssueAdapterEventListener {

            override fun updateIssueState(itemId: Int, boolean: Boolean) {
//                viewLifecycleOwner.lifecycleScope.launch {
//                    IssueHomeViewModel.updateIssueSate(itemId, boolean)
//                }
            }

            override fun switchToEditMode(itemId: Int) {
                viewModel.clearCheckList()
                adapter.isEditMode = true
                binding.tbIssues.visibility = View.GONE
                binding.clIssueEdit.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
                Log.d("TEST", "체크박스 사이즈${viewModel.checkList.value.size}")
            }

            override fun addInCheckList(itemId: Int) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.addCheckList(itemId)
                    setSelectedIssueCount()
                    Log.d("TEST", "체크박스체크${viewModel.checkList.value.size}")
                }
            }

            override fun deleteInCheckList(itemId: Int) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.removeCheckList(itemId)
                    setSelectedIssueCount()
                    Log.d("TEST", "체크박스해제${viewModel.checkList.value.size}")
                }
            }

            override fun getIntoDetail(issue: Issue) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun setSelectedIssueCount() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.checkList.collect {
                    binding.tvIssueCount.text = it.size.toString()
                }
            }
        }
    }

    private fun closeEditMode() {
        binding.btnIssueEditClose.setOnClickListener {
            adapter.isEditMode = false
            binding.tbIssues.visibility = View.VISIBLE
            binding.clIssueEdit.visibility = View.GONE
            adapter.notifyDataSetChanged()
        }
    }

    private fun removeIssue() {
        binding.btnIssueRemove.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.removeIssueList()
            }
            adapter.isEditMode = false
            binding.tbIssues.visibility = View.VISIBLE
            binding.clIssueEdit.visibility = View.GONE
            adapter.notifyDataSetChanged()
            Log.d("TEST", "삭제후 이슈리스트 사이즈${viewModel.issueList.value.size}")
        }
    }

    private fun closeIssueList() {
        binding.btnIssueClose.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.closeIssueList()
            }
            adapter.isEditMode = false
            binding.tbIssues.visibility = View.VISIBLE
            binding.clIssueEdit.visibility = View.GONE
            adapter.notifyDataSetChanged()
            Log.d("TEST", "닫힌 이슈리스트 사이즈${viewModel.closeIssueList.value.size}")
        }
    }

    private fun changeStatusBarSkyBLue() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.skyBlue)
    }

    private fun changeStatusBarWhite() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun moveToDetail(id: Int) {
        navigator.navigate(
            R.id.action_navigation_issue_to_issueDetailFragment,
            bundleOf(Constants.ISSUE_ID_KEY to id)
        )
    }

    override fun onStop() {
        super.onStop()
        changeStatusBarWhite()
    }
}