package com.example.issue_tracker.ui

import androidx.lifecycle.ViewModel
import com.example.issue_tracker.domain.model.Label
import com.example.issue_tracker.domain.model.MileStone
import com.example.issue_tracker.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class HomeViewModel : ViewModel() {
    private val _labelList = MutableStateFlow<List<Label>>(listOf())
    val labelList: StateFlow<List<Label>> = _labelList

    private val _mileStoneList = MutableStateFlow<List<MileStone>>(listOf())
    val mileStoneList: StateFlow<List<MileStone>> = _mileStoneList

    private val _userList = MutableStateFlow<List<User>>(listOf())
    val userList: StateFlow<List<User>> = _userList

    init {
        makeDummyLabels()
        makeDummyMileStones()
        makeDummyUser()
    }


    private fun makeDummyUser() {
        val users = mutableListOf<User>()
        for (i in 0..10) {
            users.add(User(i, "User${i}"))
        }
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
}