package com.example.mvvmex.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmex.databinding.RecyclerviewItemBinding
import com.example.mvvmex.entity.MovieResult

class MovieAdapter(
    private val onItemClick: (MovieResult.Item) -> Unit,
    private val onItemLongClick: (MovieResult.Item) -> Unit
) :
    ListAdapter<MovieResult.Item, MovieAdapter.MovieViewHolder>(MovieDiffUtil) {
    class MovieViewHolder(
        private val parent: ViewGroup,
        onItemClick: (MovieResult.Item) -> Unit,
        onItemLongClick: (MovieResult.Item) -> Unit
    ) : RecyclerView.ViewHolder(
        RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {
        private val binding: RecyclerviewItemBinding = DataBindingUtil.bind(itemView) ?: throw IllegalStateException("fail to bind")
        private lateinit var item: MovieResult.Item

        init {
            itemView.setOnClickListener {
                onItemClick(item)
            }
            itemView.setOnLongClickListener {
                onItemLongClick(item)
                true
            }
        }

        fun bind(item: MovieResult.Item) {
            this.item = item
            binding.movie = item
            binding.executePendingBindings()
        }
    }

    object MovieDiffUtil : DiffUtil.ItemCallback<MovieResult.Item>() {
        override fun areItemsTheSame(oldItem: MovieResult.Item, newItem: MovieResult.Item): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieResult.Item, newItem: MovieResult.Item): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(parent, onItemClick, onItemLongClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
