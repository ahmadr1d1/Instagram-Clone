package com.ahmadrd.instagramclone.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.ReelsDesignBinding
import com.ahmadrd.instagramclone.models.Reels
import com.squareup.picasso.Picasso

class ReelsAdapter(var context: Context)
    : ListAdapter<Reels, ReelsAdapter.ViewHolder>(DIFF_CALLBACK) {

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

    inner class ViewHolder(var binding: ReelsDesignBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(reels: Reels) {
            Picasso.get()
                .load(reels.profileLink)
                .placeholder(R.drawable.user)
                .into(binding.profileImage)

            with(binding) {
                caption.text = reels.caption
                videoView.setVideoPath(reels.reelsUrl)
                videoView.setOnPreparedListener {
                    progressBar.visibility = View.GONE
                    videoView.start()
                    it.isLooping = true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReelsDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}