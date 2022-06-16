package com.example.issue_tracker.ui.issue.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemIssuesBinding

class IssueAdapter : RecyclerView.Adapter<IssueAdapter.ViewHolder>() {

    private val items = mutableListOf<Issue>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemIssuesBinding.inflate(inflater, parent, false))
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ItemIssuesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemIssue: Issue) {
            binding.itemIssue = itemIssue
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitList(items: List<Issue>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun closeIssue(position: Int): Issue {
        val tempIssue = items[position]
        items.removeAt(position)
        return tempIssue
    }
}