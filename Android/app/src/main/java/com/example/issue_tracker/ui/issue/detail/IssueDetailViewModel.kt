package com.example.issue_tracker.ui.issue.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.domain.model.CommentItem
import com.example.issue_tracker.domain.model.IssueDetail
import com.example.issue_tracker.domain.repository.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
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


    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e("Error", ": ${throwable.message}")
        }


    fun loadDetail(id:Int){
        viewModelScope.launch(coroutineExceptionHandler){
            async {
                _issueDetail.value = repository.loadDetail(id)

            }.await()
        }
    }

    fun loadComments(id:Int, page:Int){
        viewModelScope.launch(coroutineExceptionHandler) {
            _comments.emit(repository.loadComments(id,page))

        }
    }

}