package com.example.issue_tracker.ui.issue.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemCommentMyselfBinding
import com.example.issue_tracker.databinding.ItemCommentOtherBinding
import com.example.issue_tracker.domain.model.Comment
import com.example.issue_tracker.domain.model.MyComment
import com.example.issue_tracker.domain.model.OtherComment
import com.example.issue_tracker.ui.common.getTimeDiff


const val VIEW_TYPE_MY_COMMENT = 0
const val VIEW_TYPE_OTHER_COMMENT = 1

class IssueDetailAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val comments = mutableListOf<Comment>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            VIEW_TYPE_MY_COMMENT-> MyCommentViewHolder(ItemCommentMyselfBinding.inflate(inflater,parent,false))
            else-> OtherCommentViewHolder(ItemCommentOtherBinding.inflate(inflater,parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MyCommentViewHolder ->{
                val item=  comments[position] as MyComment
                holder.bind(item)
            }
            is OtherCommentViewHolder->{
                val item= comments[position] as OtherComment
                holder.bind(item)
            }

        }
    }

    inner class MyCommentViewHolder(private val binding: ItemCommentMyselfBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: MyComment) {
            binding.comment= comment
            binding.logTime = getTimeDiff(comment.time)
        }
    }
    inner class OtherCommentViewHolder(private val binding:ItemCommentOtherBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(comment: OtherComment){
            binding.comment= comment
            binding.logTime = getTimeDiff(comment.time)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (comments[position]) {
            is MyComment -> VIEW_TYPE_MY_COMMENT
            else -> VIEW_TYPE_OTHER_COMMENT
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    fun submitList(items:List<Comment>){
        this.comments.addAll(items)
    }
}