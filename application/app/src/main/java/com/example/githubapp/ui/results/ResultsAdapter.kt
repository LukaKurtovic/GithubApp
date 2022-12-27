package com.example.githubapp.ui.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.data.model.GithubRepository
import com.example.githubapp.databinding.ListItemBinding

class ResultsAdapter(private val listener: OnItemClickListener) :
    ListAdapter<GithubRepository, ResultsAdapter.ResultsViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultsViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ResultsViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: GithubRepository) {
            binding.apply {
                tvAuthor.text = repo.full_name
                tvDescription.text = repo.description
                tvNumberOfStars.text = repo.starsFormatted
                root.setOnClickListener {
                    listener.onItemClick(repo)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(repo: GithubRepository)
    }

    class DiffCallback : DiffUtil.ItemCallback<GithubRepository>() {
        override fun areItemsTheSame(oldItem: GithubRepository, newItem: GithubRepository) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GithubRepository, newItem: GithubRepository) =
            oldItem == newItem
    }
}