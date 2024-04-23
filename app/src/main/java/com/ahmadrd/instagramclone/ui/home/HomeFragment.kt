package com.ahmadrd.instagramclone.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.FragmentHomeBinding
import com.ahmadrd.instagramclone.models.Post
import com.ahmadrd.instagramclone.models.User
import com.ahmadrd.instagramclone.ui.adapter.HomePostAdapter
import com.ahmadrd.instagramclone.ui.adapter.StoryFollowAdapter
import com.ahmadrd.instagramclone.utils.Constant
import com.ahmadrd.instagramclone.utils.Constant.FOLLOW
import com.ahmadrd.instagramclone.utils.Constant.POST
import com.ahmadrd.instagramclone.utils.Constant.TIME
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomePostAdapter
    private lateinit var followAdapter: StoryFollowAdapter
    private var postList = ArrayList<Post>()
    private val listFollow = ArrayList<User>()

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.imgAdd.setImageResource(R.drawable.plus_blue)

        adapter = HomePostAdapter(requireContext())
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.adapter = adapter

        binding.rvStoryFollow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        followAdapter = StoryFollowAdapter(requireContext())
        binding.rvStoryFollow.adapter = followAdapter

        Firebase.firestore.collection(POST)
            .orderBy(TIME, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                postList.clear()
                for (document in documents) {
                    val post = document.toObject<Post>()
                    postList.add(post)
                }
                adapter.submitList(postList)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents: ", exception)
            }


        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
            .get().addOnSuccessListener {
                listFollow.clear()
                for (i in it) {
                    val user = i.toObject<User>()
                    listFollow.add(user)
                }
                followAdapter.submitList(listFollow)
            }
            .addOnFailureListener { exception ->
                // Handle error jika terjadi
                Log.e(TAG, "Error getting documents: ", exception)
            }

        val toolbar = binding.toolbarHome
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.like -> {
                    findNavController().navigate(R.id.action_navigation_home_to_notificationFragment)
                }
                R.id.message -> {
                    val intent = Intent(requireContext(), MessageActivity::class.java)
                    activity?.startActivity(intent)
                }
            }
            true
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(Constant.USER).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user: User = it.toObject<User>()!!

                if(!user.image.isNullOrEmpty()) {
                    Picasso.get().load(user.image).into(binding.storyProfileImage)
                }
            }
    }
}