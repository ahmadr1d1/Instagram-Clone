package com.ahmadrd.instagramclone.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.RvPostBinding
import com.ahmadrd.instagramclone.models.Post
import com.ahmadrd.instagramclone.models.User
import com.ahmadrd.instagramclone.ui.bottomsheet.MorePost
import com.ahmadrd.instagramclone.utils.Constant.USER
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class HomePostAdapter(private val context: Context) :
    ListAdapter<Post, HomePostAdapter.MyHolder>(PostDiffCallback()) {

    companion object {
        private const val TAG = "HomePostAdapter"
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postUrl == newItem.postUrl
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    inner class MyHolder(private val binding: RvPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            try {
                Firebase.firestore.collection(USER).document(post.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        val user = document.toObject<User>()
                        user?.let {
                            Glide.with(context)
                                .load(it.image)
                                .placeholder(R.drawable.user)
                                .into(binding.profileImage)
                            binding.name.text = it.name
                            binding.tvBotName.text = it.name
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Handle error jika terjadi
                        Toast.makeText(context,
                            "Error fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error getting documents: ", exception)
                    }

            } catch (e: Exception) {
                Log.d("HomePostAdapter - UidUser", e.message.toString())
            }

            Glide.with(context)
                .load(post.postUrl)
                .placeholder(R.drawable.refresh)
                .override(1080, 1080)
                .centerCrop()
                .into(binding.postImage)

            try {
                val text = TimeAgo.using(post.time.toLong())
                binding.time.text = text
            } catch (e: Exception) {
                Log.d("HomePostAdapter - Time", "${e.message} because it's Long")
            }

            with(binding) {
                likesImage.setOnClickListener {
                    likesImage.setImageResource(R.drawable.red_love)
                }
                bookmarkImage.setOnClickListener {
                    bookmarkImage.setImageResource(R.drawable.saved_bookmark)
                }
                sendImage.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, post.postUrl)
                    context.startActivity(intent)
                }
                caption.text = post.caption

                more.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString(MorePost.KEY, "value")
                    val bottomSheetDialogFragment = MorePost()
                    bottomSheetDialogFragment.arguments = bundle
                    bottomSheetDialogFragment.show(
                        (context as FragmentActivity).supportFragmentManager,
                        bottomSheetDialogFragment.tag
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = RvPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
