package com.ahmadrd.instagramclone.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.RvFollowBinding
import com.ahmadrd.instagramclone.models.User
import com.bumptech.glide.Glide

class StoryFollowAdapter(private val context: Context) :
    ListAdapter<User, StoryFollowAdapter.MyHolder>(PostDiffCallback()) {

    class PostDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    inner class MyHolder(var binding: RvFollowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide.with(context)
                .load(user.image)
                .placeholder(R.drawable.user)
                .into(binding.imageView3)
            binding.name.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = RvFollowBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(getItem(position))
    }
}