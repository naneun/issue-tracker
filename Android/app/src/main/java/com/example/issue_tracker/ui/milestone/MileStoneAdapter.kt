package com.example.issue_tracker.ui.milestone


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemMilestoneBinding
import com.example.issue_tracker.domain.model.MileStone


class MilestoneAdapter() : ListAdapter<MileStone, MilestoneAdapter.ViewHolder>(MilestoneDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMilestoneBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MilestoneAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMilestoneBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MileStone) {
            binding.milestone = item
            binding.totalCount = item.closeIssueCount + item.openIssueCount

            binding.clMilestone.setOnClickListener {

            }
        }
    }

    companion object MilestoneDiffUtil : DiffUtil.ItemCallback<MileStone>() {
        override fun areItemsTheSame(oldItem: MileStone, newItem: MileStone): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MileStone, newItem: MileStone): Boolean {
            return oldItem == newItem
        }

    }
}