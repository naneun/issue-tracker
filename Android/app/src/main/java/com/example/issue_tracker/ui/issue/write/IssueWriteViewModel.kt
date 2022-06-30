package com.example.issue_tracker.ui.issue.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.domain.model.IssueRequestDto
import com.example.issue_tracker.domain.repository.IssueRepository
import com.example.issue_tracker.ui.common.FormDataUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class IssueWriteViewModel @Inject constructor(private val repository: IssueRepository):ViewModel() {

    private val _selectedLabel = MutableStateFlow<Int>(0)
    val selectedLabel = _selectedLabel.asStateFlow()

    private val _selectedWriter = MutableStateFlow(0)
    val selectedWriter = _selectedWriter.asStateFlow()

    private val _selectedMileStone = MutableStateFlow(0)
    val selectedMileStone = _selectedMileStone.asStateFlow()

    private val _imageUrl = MutableStateFlow("")
    val imageUrl  = _imageUrl.asStateFlow()

    fun registerIssue(title:String, content:String){
        viewModelScope.launch {
            repository.registerIssue(IssueRequestDto(selectedWriter.value, content, selectedLabel.value, selectedMileStone.value, title))
        }
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

    fun loadImage(file: File){
        viewModelScope.launch {
            _imageUrl.emit(repository.loadImage(FormDataUtil.getImageMultipart("image", file)))
        }
    }

}