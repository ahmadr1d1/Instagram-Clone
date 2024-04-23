package com.ahmadrd.instagramclone.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.instagramclone.databinding.RvMessageBinding
import com.ahmadrd.instagramclone.models.Post

class MessageAdapter(val context: Context, private val chatList: ArrayList<Post>)
    :RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    inner class ViewHolder(var binding: RvMessageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}