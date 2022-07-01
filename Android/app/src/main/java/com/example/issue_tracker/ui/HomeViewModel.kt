package com.example.issue_tracker.ui

import android.util.Log
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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
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

    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e("Error", ": ${throwable.message}")
        }

    init {
        loadLabelList()
        loadMileStoneList()
        loadUserList()
        initStateList()
    }

     fun loadLabelList(){
        viewModelScope.launch(coroutineExceptionHandler) {
            _labelList.emit(repository.getLabelList().toMutableList())
        }
    }


    fun loadMileStoneList(){
        viewModelScope.launch(coroutineExceptionHandler) {
            _mileStoneList.emit(repository.getMileStoneList())
        }
    }
    
    private fun loadUserList(){
        viewModelScope.launch(coroutineExceptionHandler) {
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
    

    fun clearCheckList() {
        _editCheckList.value.clear()
    }

    fun addCheckList(itemId: Int) {
        _editCheckList.value.add(itemId)
        labelEditModeOn()
    }

    fun removeCheckList(itemId: Int) {
        _editCheckList.value.removeIf {
            it == itemId
        }
        labelEditModeOff()
    }

    fun removeLabelList() {
        _labelEditMode.value = false
        viewModelScope.launch(coroutineExceptionHandler) {
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
        return _loginUser.value!=null
    }

    fun getWriterID(name:String): Int {
        _userList.value.forEach {
            if(it.name==name){
                return it.id
            }
        }
        return 0
    }

    fun getLabelID(title:String):Int{
        _labelList.value.forEach {
            if(it.title==title){
                return it.id
            }
        }
        return 0
    }

    fun getMileStoneID(title:String): Int {
        _mileStoneList.value.forEach {
            if(it.title==title){
                return it.id
            }
        }
        return 0
    }

    fun getIssueState(title: String) : IssueState{
        _stateList.value.forEach {
            if(it.value == title){
                return it
            }
        }
        return IssueState.OPEN
    }
}