package com.ahmadrd.instagramclone.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.instagramclone.databinding.RvMypostBinding
import com.ahmadrd.instagramclone.models.Post
import com.bumptech.glide.Glide

class MyPostAdapter(private val context: Context) :
    ListAdapter<Post, MyPostAdapter.ViewHolder>(PostDiffCallback()) {

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postUrl == newItem.postUrl
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(var binding: RvMypostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            Glide.with(context)
                .load(post.postUrl)
                .disallowHardwareConfig()
                .into(binding.postFile)

            binding.postFile.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvMypostBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}