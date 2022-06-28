package com.example.issue_tracker.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.issue_tracker.domain.model.Label
import com.example.issue_tracker.domain.model.MileStone
import com.example.issue_tracker.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class HomeViewModel : ViewModel() {
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

    init {
        makeDummyLabels()
        makeDummyMileStones()
        makeDummyUser()
    }

    private fun makeDummyUser() {
        val users = mutableListOf<User>()
        for (i in 0..10) {
            users.add(User(i, "User${i}", "https://images.unsplash.com/photo-1655057011043-158c48f3809d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEyfHhqUFI0aGxrQkdBfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60")) }
        _userList.value = users
    }

    private fun makeDummyLabels() {
        val labels = mutableListOf<Label>()
        for (i in 0..10) {
            labels.add(Label(i, "제목${i}", "내용입니다", randomHexColor()))
        }
        _labelList.value = labels
    }

    private fun randomHexColor(): String {
        val redValue = Random.nextInt(256).toString(16)
        val greenValue = Random.nextInt(256).toString(16)
        val blueValue = Random.nextInt(256).toString(16)
        return "FF${checkHexLength(redValue)}${checkHexLength(greenValue)}${checkHexLength(blueValue)}"
    }

    private fun checkHexLength(RGBValue: String): String {
        return if (RGBValue.length < 2) {
            "0${RGBValue}"
        } else {
            RGBValue
        }
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
        for (i in 0 until _editCheckList.value.size) {
            _labelList.value.removeIf {
                it.id == _editCheckList.value[i]
            }
        }
    }

    fun labelEditModeOn() {
        _labelEditMode.value = true
    }

    fun labelEditModeOff() {
        _labelEditMode.value = false
    }
}