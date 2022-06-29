package com.example.issue_tracker.ui.issue.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemIssuesBinding
import com.example.issue_tracker.domain.model.Issue

class IssueAdapter(private val itemClick: (selectedIssueID: Int) -> Unit) :
    ListAdapter<Issue, IssueAdapter.ViewHolder>(IssueDiffUtil) {
    var issueAdapterEventListener: IssueAdapterEventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemIssuesBinding.inflate(inflater, parent, false))
    }


    inner class ViewHolder(private val binding: ItemIssuesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemIssue: Issue, itemClick: (selectedIssueID: Int) -> Unit) {

            if (itemIssue.editable) {
                binding.cbIssueSelector.visibility = View.VISIBLE
            } else {
                binding.cbIssueSelector.visibility = View.GONE
                binding.cbIssueSelector.isChecked = false
            }


            binding.clSwipeView.setOnClickListener {
                itemClick.invoke(itemIssue.id)
            }
            binding.itemIssue = itemIssue
            binding.labelColor= "FF${itemIssue.label.backgroundColor.replace("#","")}".toLong(16).toInt()
            binding.clSwipeView.setOnLongClickListener {
                issueAdapterEventListener?.switchToEditMode(itemIssue.id)
                issueAdapterEventListener
                true
            }

            binding.cbIssueSelector.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    issueAdapterEventListener?.addInCheckList(itemIssue.id)
                    binding.cbIssueSelector.setBackgroundColor(0xffF2F2F7.toInt())
                    binding.clSwipeView.setBackgroundColor(0xffF2F2F7.toInt())
                    binding.cbIssueSelector.buttonTintList = ColorStateList.valueOf(0xff007AFF.toInt())
                } else {
                    issueAdapterEventListener?.deleteInCheckList(itemIssue.id)
                    binding.cbIssueSelector.setBackgroundColor(0xffffffff.toInt())
                    binding.clSwipeView.setBackgroundColor(0xffffffff.toInt())
                    binding.cbIssueSelector.buttonTintList = ColorStateList.valueOf(0xffD5D5DB.toInt())
                }
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClick)
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