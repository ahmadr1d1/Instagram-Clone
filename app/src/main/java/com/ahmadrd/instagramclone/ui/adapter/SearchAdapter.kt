package com.ahmadrd.instagramclone.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.RvSearchBinding
import com.ahmadrd.instagramclone.models.User
import com.ahmadrd.instagramclone.utils.Constant.FOLLOW
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SearchAdapter(var context: Context):
    ListAdapter<User, SearchAdapter.MyHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.email == newItem.email
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
        const val UNFOLLOW = "Unfollow"
    }

    inner class MyHolder(var binding: RvSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            var isFollow = false
            Glide.with(context)
                .load(user.image)
                .placeholder(R.drawable.user)
                .into(binding.profileImage)

            with(binding) {
                name.text = user.name
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                    .whereEqualTo("email", user.email)
                    .get().addOnSuccessListener {
                        if (it.documents.size == 0) {
                            isFollow = false
                            Log.d("SearchAdapter", "email empty")
                        } else {
                            binding.follow.text =
                                UNFOLLOW
                            isFollow = true
                        }
                    }
                follow.setOnClickListener {
                    if (isFollow) {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                            .whereEqualTo("email", user.email)
                            .get().addOnSuccessListener {
                                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                                    .document(it.documents[0].id).delete()
                                binding.follow.text =
                                    FOLLOW
                                isFollow = false
                            }
                        Log.d("SearchAdapter", "Already Follow")
                    } else {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                            .document().set(user)
                        binding.follow.text =
                            UNFOLLOW
                        isFollow = true
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = RvSearchBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(getItem(position))
    }
}