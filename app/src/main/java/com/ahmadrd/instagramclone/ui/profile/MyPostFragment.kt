package com.ahmadrd.instagramclone.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ahmadrd.instagramclone.databinding.FragmentMyPostBinding
import com.ahmadrd.instagramclone.models.Post
import com.ahmadrd.instagramclone.ui.adapter.MyPostAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyPostFragment : Fragment() {

    private lateinit var binding: FragmentMyPostBinding
    private lateinit var adapter: MyPostAdapter
    private var myPostList = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentMyPostBinding.inflate(inflater, container, false)

        adapter = MyPostAdapter(requireContext())
        binding.rvMypost.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvMypost.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                myPostList.clear()
                for (i in it.documents) {
                    val post: Post = i.toObject<Post>()!!
                    myPostList.add(post)
                }
                adapter.submitList(myPostList)
            }

        return binding.root
    }
}