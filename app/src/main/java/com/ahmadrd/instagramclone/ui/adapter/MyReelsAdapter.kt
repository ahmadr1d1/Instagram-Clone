package com.ahmadrd.instagramclone.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.instagramclone.databinding.RvMyreelsBinding
import com.ahmadrd.instagramclone.models.Reels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MyReelsAdapter(var context: Context):
    ListAdapter<Reels, MyReelsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Reels>() {
            override fun areItemsTheSame(oldItem: Reels, newItem: Reels): Boolean {
                return oldItem.reelsUrl == newItem.reelsUrl
            }

            override fun areContentsTheSame(oldItem: Reels, newItem: Reels): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(var binding: RvMyreelsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(reels: Reels) {
            Glide.with(context)
                .load(reels.reelsUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.postFile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvMyreelsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}