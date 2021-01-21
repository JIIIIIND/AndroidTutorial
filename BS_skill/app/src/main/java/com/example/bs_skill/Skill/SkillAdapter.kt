package com.example.bs_skill.Skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bs_skill.Data.Skill
import com.example.bs_skill.databinding.LinearViewItemBinding

class SkillAdapter :
    ListAdapter<Skill, SkillAdapter.SkillViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        return SkillViewHolder(LinearViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val skillItem = getItem(position)
        holder.bind(skillItem)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Skill>() {
        override fun areItemsTheSame(oldItem: Skill, newItem: Skill): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Skill, newItem: Skill): Boolean {
            return oldItem.name == newItem.name
        }
    }

    class SkillViewHolder(private var binding: LinearViewItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(skill: Skill) {
            binding.skill = skill
            binding.executePendingBindings()
        }
    }
}