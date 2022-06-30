package com.example.issue_tracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.domain.model.IssueState
import com.example.issue_tracker.domain.model.Label
import com.example.issue_tracker.domain.model.MileStone
import com.example.issue_tracker.domain.model.User
import com.example.issue_tracker.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val _labelList = MutableStateFlow<MutableList<Label>>(mutableListOf())
    val labelList: StateFlow<List<Label>> = _labelList

    private val _mileStoneList = MutableStateFlow<List<MileStone>>(listOf())
    val mileStoneList: StateFlow<List<MileStone>> = _mileStoneList

    private val _userList = MutableStateFlow<List<User>>(listOf())
    val userList: StateFlow<List<User>> = _userList

    private val _editCheckList = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val editCheckList: StateFlow<List<Int>> = _editCheckList

    private val _labelEditMode = MutableStateFlow<Boolean>(false)
    val labelEditMode: StateFlow<Boolean> = _labelEditMode

    private val _stateList = MutableStateFlow<List<IssueState>>(listOf())
    val stateList: StateFlow<List<IssueState>> = _stateList

    private val _loginUser = MutableLiveData<User>()
    val loginUser:LiveData<User> = _loginUser

    private val _loginMethod = MutableLiveData<String>()
    val loginMethod:LiveData<String> = _loginMethod


    init {
        loadLabelList()
        makeDummyMileStones()
        loadUserList()
        initStateList()
    }

     fun loadLabelList(){
        viewModelScope.launch {
            _labelList.emit(repository.getLabelList().toMutableList())
        }
    }

    private fun loadUserList(){
        viewModelScope.launch {
            _userList.emit(repository.getUserList())
        }
    }

    private fun initStateList() {
        _stateList.value = listOf(
            IssueState.OPEN,
            IssueState.WRITE_MYSELF,
            IssueState.ASSIGN_MYSELF,
            IssueState.WRITE_COMMENT,
            IssueState.CLOSE
        )
    }


    private fun makeDummyMileStones() {
        val milestones = mutableListOf<MileStone>()
        for (i in 0..10) {
            milestones.add(MileStone(i, "마일스톤 제목${i}", "마일스톤 더미 콘테츠", "06-14", 2, 2))
        }
        _mileStoneList.value = milestones
    }

    fun clearCheckList() {
        _editCheckList.value.clear()
    }

    fun addCheckList(itemId: Int) {
        _editCheckList.value.add(itemId)
    }

    fun removeCheckList(itemId: Int) {
        _editCheckList.value.removeIf {
            it == itemId
        }
    }

    fun removeLabelList() {
        _labelEditMode.value = false
        viewModelScope.launch {
            repository.deleteLabels(editCheckList.value)
            loadLabelList()
        }
    }

    fun labelEditModeOn() {
        _labelEditMode.value = true
    }

    fun labelEditModeOff() {
        _labelEditMode.value = false
    }

    fun saveLoginUser(id:Int){
        println(id)
        _userList.value.forEach {
            if(it.id == id){
                _loginUser.value = it
            }
        }
    }

    fun saveLoginMethod(method:String){
        _loginMethod.value = method
    }

    fun checkLogin():Boolean{
        println(_loginUser.value)
        return _loginUser.value!=null
    }
}