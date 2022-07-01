package com.example.issue_tracker.ui.issue.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemCommentMyselfBinding
import com.example.issue_tracker.databinding.ItemCommentOtherBinding
import com.example.issue_tracker.databinding.ItemCommentProgressbarBinding
import com.example.issue_tracker.domain.model.CommentItem
import com.example.issue_tracker.ui.common.getTimeDiff


const val VIEW_TYPE_MY_COMMENT = 0
const val VIEW_TYPE_OTHER_COMMENT = 1
const val VIEW_TYPE_PROGRESS = 2

class IssueDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val comments = mutableListOf<CommentItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MY_COMMENT -> MyCommentViewHolder(ItemCommentMyselfBinding.inflate(inflater, parent, false))
            VIEW_TYPE_PROGRESS-> LoadingViewHolder(ItemCommentProgressbarBinding.inflate(inflater,parent,false))
            else -> OtherCommentViewHolder(ItemCommentOtherBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyCommentViewHolder -> {
                holder.bind(comments[position] as CommentItem.Comment)
            }
            is OtherCommentViewHolder -> {
                holder.bind(comments[position]  as CommentItem.Comment)
            }
            is LoadingViewHolder->{
                holder.bind()
            }

        }
    }

    inner class MyCommentViewHolder(private val binding: ItemCommentMyselfBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: CommentItem.Comment) {
            binding.comment = comment
            binding.logTime = getTimeDiff(comment.time)
        }
    }

    inner class OtherCommentViewHolder(private val binding: ItemCommentOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentItem.Comment) {
            binding.comment = comment
            binding.logTime = getTimeDiff(comment.time)
        }
    }

    inner class LoadingViewHolder(private val binding:ItemCommentProgressbarBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(){
            binding.commentProgressBarr.isVisible = true
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (comments[position]) {
            is CommentItem.Comment -> {
                val item= comments[position] as CommentItem.Comment
                if(item.editable){
                    VIEW_TYPE_MY_COMMENT
                }
                else{
                    VIEW_TYPE_OTHER_COMMENT
                }
            }
            else ->{
                VIEW_TYPE_PROGRESS
            }
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    fun submitList(items: List<CommentItem>) {
        this.comments.addAll(items)
        this.comments.add(CommentItem.CommentProgressBar)
    }

    fun deleteLoading(){
        comments.removeAt(comments.lastIndex)
    }
}