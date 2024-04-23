package com.ahmadrd.instagramclone.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ahmadrd.instagramclone.databinding.FragmentMyReelsBinding
import com.ahmadrd.instagramclone.models.Reels
import com.ahmadrd.instagramclone.ui.adapter.MyReelsAdapter
import com.ahmadrd.instagramclone.utils.Constant.REELS
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyReelsFragment : Fragment() {

    private lateinit var binding: FragmentMyReelsBinding
    private lateinit var adapter: MyReelsAdapter
    private val myReelsList = ArrayList<Reels>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyReelsBinding.inflate(inflater, container, false)

        adapter = MyReelsAdapter(requireContext())
        binding.rvMyreels.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvMyreels.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REELS)
            .get().addOnSuccessListener {
                myReelsList.clear()
                for (i in it.documents) {
                    val reels: Reels = i.toObject<Reels>()!!
                    myReelsList.add(reels)
                }
                adapter.submitList(myReelsList)
            }

        return binding.root
    }
}