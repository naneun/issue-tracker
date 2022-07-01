package com.example.issue_tracker.ui.label

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelWriteViewModel @Inject constructor(private val repository: HomeRepository) :
    ViewModel() {

    private val _labelTitle = MutableStateFlow("")
    val labelTitle = _labelTitle.asStateFlow()

    private val _labelContent = MutableStateFlow("")
    val labelContent = _labelContent.asStateFlow()

    private val _labelColor = MutableStateFlow("")
    val labelColor = _labelColor.asStateFlow()


     fun registerLabel() {
        viewModelScope.launch {
            repository.registerLabel(labelTitle.value, labelContent.value, labelColor.value)
        }
    }

    fun loadTitle(title: String) {
        _labelTitle.value = title
    }

    fun loadContent(content: String) {
        _labelContent.value = content
    }

    fun loadColor(color: String) {
        _labelColor.value = color
    }
}