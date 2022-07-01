package com.example.issue_tracker.ui.issue.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.domain.model.CommentItem
import com.example.issue_tracker.domain.model.IssueDetail
import com.example.issue_tracker.domain.repository.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueDetailViewModel @Inject constructor(private val repository: IssueRepository):ViewModel() {

    private val _issueDetail = MutableLiveData<IssueDetail>()
    val issueDetail:LiveData<IssueDetail> = _issueDetail

    private val _comments = MutableStateFlow<List<CommentItem>>(listOf())
    val comments = _comments.asStateFlow()

    fun loadDetail(id:Int){
        viewModelScope.launch {
            _issueDetail.value = repository.loadDetail(id)
        }
    }

    fun loadComments(id:Int, page:Int){
        viewModelScope.launch {
            _comments.emit(repository.loadComments(id,page))
        }
    }

}