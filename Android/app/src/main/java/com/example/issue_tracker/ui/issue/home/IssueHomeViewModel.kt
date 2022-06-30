package com.example.issue_tracker.ui.issue.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.domain.model.Issue
import com.example.issue_tracker.domain.model.IssueState
import com.example.issue_tracker.domain.repository.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueHomeViewModel @Inject constructor(private val repository: IssueRepository) :
    ViewModel() {

    private val _openIssueList = MutableStateFlow<MutableList<Issue>>(mutableListOf())
    val openIssueList: StateFlow<List<Issue>> = _openIssueList

    private val _closeIssueList = MutableStateFlow<MutableList<Issue>>(mutableListOf())
    val closeIssueList: StateFlow<List<Issue>> = _closeIssueList

    private val _checkList = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val checkList: StateFlow<List<Int>> = _checkList

    private val _editMode = MutableStateFlow<Boolean>(false)
    val editMode = _editMode.asStateFlow()

    private val _selectedState = MutableStateFlow<IssueState>(IssueState.OPEN)
    val selectedState = _selectedState.asStateFlow()

    private val _selectedLabel = MutableStateFlow<Int>(0)
    val selectedLabel = _selectedLabel.asStateFlow()

    private val _selectedWriter = MutableStateFlow(0)
    val selectedWriter = _selectedWriter.asStateFlow()

    private val _selectedMileStone = MutableStateFlow(0)
    val selectedMileStone = _selectedMileStone.asStateFlow()

    private val _changedFlag = MutableLiveData<Boolean>(false)
    val changeFlag :LiveData<Boolean> = _changedFlag

    private val _stateChangeFlag = MutableLiveData<Boolean>(false)
    val stateChangeFlag :LiveData<Boolean> = _stateChangeFlag

    private val _initialFlag =  MutableLiveData<Boolean>(false)
    val initialFlag:LiveData<Boolean> = _initialFlag

    init {
        loadOpenIssueList()
    }

    fun loadOpenIssueList() {
        viewModelScope.launch {
            _openIssueList.emit(repository.getIssueList().toMutableList())
        }
    }

    fun addCheckList(itemId: Int) {
        _checkList.value.add(itemId)
    }

    fun removeCheckList(itemId: Int) {
        _checkList.value.removeIf {
            it == itemId
        }
    }

    fun clearCheckList() {
        _checkList.value.clear()
    }

    fun closeIssueList() {
        for (i in 0 until _checkList.value.size) {
            val str = _openIssueList.value.filter {
                it.id == _checkList.value[i]
            }
            _openIssueList.value.removeIf {
                it.id == checkList.value[i]
            }
            _closeIssueList.value.add(str[0])
        }
    }

    fun removeIssueList() {
        for (i in 0 until _checkList.value.size) {
            _openIssueList.value.removeIf {
                it.id == _checkList.value[i]
            }
        }
    }

    fun turnOnEditMode() {
        _editMode.value = true
    }

    fun turnOffEditMode() {
        _editMode.value = false
    }

    fun selectState(state: IssueState){
        _selectedState.value = state
    }

    fun selectLabel(id:Int){
        _selectedLabel.value = id
    }

    fun selectMileStone(id:Int){
        _selectedMileStone.value = id
    }

    fun selectWriter(id:Int){
        _selectedWriter.value = id
    }

    fun checkFilterChanged(){
        _changedFlag.value = (_selectedWriter.value!=0)||(_selectedLabel.value!=0)||(_selectedMileStone.value!=0)||(stateChangeFlag.value?:false)
    }

    fun setStateFlagFalse(){
        _stateChangeFlag.value =false
    }

    fun checkStateChanged(state: IssueState){
        _stateChangeFlag.value =( _selectedState.value != state)
    }

    fun checkInitialFlag(){
        _initialFlag.value = (_selectedWriter.value==0)&&(_selectedLabel.value==0)&&(_selectedMileStone.value==0)&&(_selectedState.value==IssueState.OPEN)
    }

    fun applyFilter() {
        val labelId = if (_selectedLabel.value == 0) null else _selectedLabel.value
        val writerId = if (_selectedWriter.value == 0) null else _selectedWriter.value
        val mileStoneId = if (_selectedMileStone.value == 0) null else _selectedMileStone.value
        viewModelScope.launch {
            _openIssueList.emit(repository.filterIssueList(_selectedState.value, writerId, labelId, mileStoneId).toMutableList())

        }
    }
}