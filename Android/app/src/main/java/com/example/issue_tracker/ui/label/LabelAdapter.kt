package com.example.issue_tracker.ui.label

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemLabelBinding
import com.example.issue_tracker.domain.model.Label

class LabelAdapter(
    val switchEditMode: () -> Unit,
    val addCheckList: (itemId: Int) -> Unit,
    val deleteCheckList: (itemId: Int) -> Unit,
    val switchCheckBox: (checkBox: CheckBox) -> Unit
) : ListAdapter<Label, LabelAdapter.ViewHolder>(LabelDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemLabelBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: LabelAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemLabelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Label) {
            binding.label = item
            binding.labelColor = item.backgroundColor.toLong(16).toInt()

            switchCheckBox(binding.clLabelSelector)

            binding.clLabel.setOnLongClickListener {
                switchEditMode()
                true
            }

            binding.clLabelSelector.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    addCheckList(item.id)
                    binding.clLabelSelector.setBackgroundColor(0xffF2F2F7.toInt())
                    binding.clLabel.setBackgroundColor(0xffF2F2F7.toInt())
                    binding.clLabelSelector.buttonTintList =
                        ColorStateList.valueOf(0xff007AFF.toInt())
                } else {
                    deleteCheckList(item.id)
                    binding.clLabelSelector.setBackgroundColor(0xffffffff.toInt())
                    binding.clLabel.setBackgroundColor(0xffffffff.toInt())
                    binding.clLabelSelector.buttonTintList =
                        ColorStateList.valueOf(0xffD5D5DB.toInt())
                }
            }
        }
    }

    companion object LabelDiffUtil : DiffUtil.ItemCallback<Label>() {
        override fun areItemsTheSame(oldItem: Label, newItem: Label): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Label, newItem: Label): Boolean {
            return oldItem == newItem
        }

    }

}