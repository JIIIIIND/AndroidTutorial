package com.example.githubsearch.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.GithubListItemBinding
import com.example.githubsearch.network.Item

class GithubListAdapter(private val onClickListener: OnClickListener ) : ListAdapter<Item,
        GithubListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubListAdapter.ItemViewHolder {
        return ItemViewHolder(GithubListItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }
    override fun onBindViewHolder(holder: GithubListAdapter.ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    class ItemViewHolder(private var binding:
                         GithubListItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.property = item
            if (item.language == null)
                item.language = "no language"
            binding.executePendingBindings()
        }
    }
    class OnClickListener(val clickListener: (item:Item) -> Unit) {
        fun onClick(item:Item) = clickListener(item)
    }
}