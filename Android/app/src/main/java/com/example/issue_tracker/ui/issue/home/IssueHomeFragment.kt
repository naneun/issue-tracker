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
import androidx.core.view.get
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
import com.example.issue_tracker.domain.model.IssueState
import com.example.issue_tracker.domain.model.SpinnerType
import com.example.issue_tracker.ui.HomeViewModel
import com.example.issue_tracker.ui.common.LoginUser
import com.example.issue_tracker.ui.common.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        setDefaultFilterMenu()
        setDefaultFilterSelection()
        closeEditMode()
        removeIssue()
        closeIssueList()
        setEditMode()
        enableApply()
        enableInitialize()
        setInitialize()
        setFilterApply()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { loadUserList()}
                launch { loadStateList() }
                launch { loadLabelList() }
                launch { loadMileStoneList() }
                launch { loadIssueList() }
            }
        }

        binding.etlSearch.setEndIconOnClickListener {
            //To do: Search 로직 (백엔드 API와 협의 필요)
        }
    }

    private fun setInitialize(){
        binding.btnFilterReset.setOnClickListener {
            if(it.isEnabled){
                setDefaultFilterSelection()
                viewModel.checkFilterChanged()
                viewModel.checkInitialFlag()
                viewModel.loadOpenIssueList()
            }
        }
    }

    private fun setFilterApply(){
        binding.btnFilterApply.setOnClickListener {
            if(it.isEnabled){
                viewModel.applyFilter()
                viewModel.setStateFlagFalse()
                binding.clIssueList.isVisible = true
                binding.clFilter.isVisible = false
                changeStatusBarWhite()
            }
        }
    }

    private fun enableInitialize(){
        viewModel.initialFlag.observe(viewLifecycleOwner){
            binding.btnFilterReset.isEnabled = !it
        }
    }

    private fun enableApply(){
        viewModel.changeFlag.observe(viewLifecycleOwner){
            binding.btnFilterApply.isEnabled = it
        }
    }


    private fun saveLoginInfo(){
        if(LoginUser.id!=""){
            sharedViewModel.saveLoginUser(LoginUser.id.toInt())
        }
        if(LoginUser.method !=""){
            sharedViewModel.saveLoginMethod(LoginUser.method)
        }
    }
    private fun setEditMode(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editMode.collect {editable->
                adapter.currentList.forEach {
                    it.editable = editable
                }
            }
        }
    }

    private fun initEditMode(){
        adapter.currentList.forEach {
            it.editable=false
        }
    }

    private fun setDefaultFilterMenu() {
        val menuList = mutableListOf(getString(R.string.spinner_default))
        setSpinner(menuList, SpinnerType.STATE)
        setSpinner(menuList, SpinnerType.WRITER)
        setSpinner(menuList, SpinnerType.LABEL)
        setSpinner(menuList, SpinnerType.MILESTONE)
    }

    private fun setDefaultFilterSelection() {
        binding.spinnerIssueState.setSelection(0)
        binding.spinnerIssueMilestone.setSelection(0)
        binding.spinnerIssueAssignee.setSelection(0)
        binding.spinnerIssueLabel.setSelection(0)
    }

    private suspend fun loadIssueList() {
        viewModel.openIssueList.collect {
            adapter.submitList(it)
        }
    }


    private suspend fun loadStateList() {
        val stateList = mutableListOf<String>()
        sharedViewModel.stateList.collect {
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
            saveLoginInfo()
            setSpinner(userList, SpinnerType.WRITER)
        }
    }

    private suspend fun loadLabelList() {
        val labelList = mutableListOf(getString(R.string.spinner_default))
        setSpinner(labelList, SpinnerType.LABEL)
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
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_filter, R.id.tv_filter_value, list)
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val text = view?.findViewById<TextView>(R.id.tv_filter_value)
                text?.setTextColor(Color.WHITE)
                view?.findViewById<View>(R.id.divider_filter_value)?.isVisible = false
                when (type) {
                    SpinnerType.STATE -> {
                        when(position){
                            0-> {
                                viewModel.checkStateChanged(IssueState.OPEN)
                                viewModel.selectState(IssueState.OPEN)
                            }
                            else-> {
                                val state = sharedViewModel.getIssueState(spinner.adapter.getItem(position) as String)
                                viewModel.checkStateChanged(state)
                                viewModel.selectState(state)
                            }
                        }
                    }
                    SpinnerType.LABEL -> {
                        when(position){
                            0-> {viewModel.selectLabel(0) }
                            else-> {
                                val labelId = sharedViewModel.getLabelID(spinner.adapter.getItem(position) as String)
                                viewModel.selectLabel(labelId)
                            }

                        }

                    }
                    SpinnerType.MILESTONE ->  {
                        when(position){
                            0->{ viewModel.selectMileStone(0)}
                            else->{
                                val mileStoneId = sharedViewModel.getMileStoneID(spinner.adapter.getItem(position) as String)
                                viewModel.selectMileStone(mileStoneId)
                            }
                        }

                    }
                    else -> {
                        when(position){
                            0-> {viewModel.selectWriter(0)}
                            else->{
                                val writerId = sharedViewModel.getWriterID(spinner.adapter.getItem(position) as String)
                                viewModel.selectWriter(writerId)
                            }
                        }

                    }
                }
                viewModel.checkFilterChanged()
                viewModel.checkInitialFlag()
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
            viewModel.checkFilterChanged()
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

            override fun switchToEditMode(itemId: Int) {
                viewModel.clearCheckList()
                viewModel.turnOnEditMode()
                binding.tbIssues.visibility = View.GONE
                binding.clIssueEdit.visibility = View.VISIBLE
                binding.rvIssue.setPadding(0, 200, 0, 0)
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
            viewModel.turnOffEditMode()
            binding.tbIssues.visibility = View.VISIBLE
            binding.clIssueEdit.visibility = View.GONE
            binding.rvIssue.setPadding(0, 0, 0, 0)
            adapter.notifyDataSetChanged()
        }
    }

    private fun removeIssue() {
        binding.btnIssueRemove.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.removeIssueList()
            }
            binding.rvIssue.setPadding(0, 0, 0, 0)
            viewModel.turnOffEditMode()
            binding.tbIssues.visibility = View.VISIBLE
            binding.clIssueEdit.visibility = View.GONE
            adapter.notifyDataSetChanged()
            Log.d("TEST", "삭제후 이슈리스트 사이즈${viewModel.openIssueList.value.size}")
        }
    }

    private fun closeIssueList() {
        binding.btnIssueClose.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.closeIssueList()
            }
            binding.rvIssue.setPadding(0, 0, 0, 0)
            viewModel.turnOffEditMode()
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
        initEditMode()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadOpenIssueList()
    }

}