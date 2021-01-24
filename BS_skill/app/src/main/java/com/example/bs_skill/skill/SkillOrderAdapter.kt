package com.example.bs_skill.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bs_skill.data.SkillOrder
import com.example.bs_skill.databinding.SkillOrderItemBinding

class SkillOrderAdapter :
        ListAdapter<SkillOrder, SkillOrderAdapter.SkillOrderViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillOrderViewHolder {
        return SkillOrderViewHolder(SkillOrderItemBinding.inflate(
                LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SkillOrderViewHolder, position: Int) {
        val skillOrderItem = getItem(position)
        holder.bind(skillOrderItem)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SkillOrder>() {
        override fun areItemsTheSame(oldItem: SkillOrder, newItem: SkillOrder): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SkillOrder, newItem: SkillOrder): Boolean {
            return oldItem.command == newItem.command
        }
    }

    class SkillOrderViewHolder(private var binding: SkillOrderItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(skillOrder: SkillOrder) {
            binding.order = skillOrder
            binding.executePendingBindings()
        }
    }
}