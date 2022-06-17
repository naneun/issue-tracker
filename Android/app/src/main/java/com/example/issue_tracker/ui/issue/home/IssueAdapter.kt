package com.example.issue_tracker.ui.issue.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemIssuesBinding
import com.example.issue_tracker.domain.model.Issue

class IssueAdapter() : ListAdapter<Issue, IssueAdapter.ViewHolder>(IssueDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemIssuesBinding.inflate(inflater, parent, false))
    }

    inner class ViewHolder(private val binding: ItemIssuesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemIssue: Issue) {
            binding.itemIssue = itemIssue
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object IssueDiffUtil : DiffUtil.ItemCallback<Issue>() {
        override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
            return oldItem == newItem
        }
    }

    fun removeData(position: Int) {
        currentList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun closeIssue(position: Int): Issue {
        val tempIssue = currentList[position]
        currentList.removeAt(position)
        return tempIssue
    }

    fun visibleIssue() {
    }
}