package com.example.issue_tracker.ui.label

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemLabelBinding
import com.example.issue_tracker.domain.model.Label

class LabelAdapter() : ListAdapter<Label, LabelAdapter.ViewHolder>(LabelDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LabelAdapter.ViewHolder(ItemLabelBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: LabelAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding:ItemLabelBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item:Label){
            binding.label= item
            binding.labelColor= item.color.toLong(16).toInt()
        }
    }

    companion object LabelDiffUtil:DiffUtil.ItemCallback<Label>(){
        override fun areItemsTheSame(oldItem: Label, newItem: Label): Boolean {
            return oldItem.id==newItem.id

        }

        override fun areContentsTheSame(oldItem: Label, newItem: Label): Boolean {
            return oldItem==newItem
        }

    }

}