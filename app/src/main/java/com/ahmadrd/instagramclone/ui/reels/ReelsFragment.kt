package com.ahmadrd.instagramclone.ui.reels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahmadrd.instagramclone.databinding.FragmentReelsBinding
import com.ahmadrd.instagramclone.models.Reels
import com.ahmadrd.instagramclone.ui.adapter.ReelsAdapter
import com.ahmadrd.instagramclone.utils.Constant.REELS
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsFragment : Fragment() {

    private lateinit var binding: FragmentReelsBinding
    private lateinit var adapter: ReelsAdapter
    private var reelList = ArrayList<Reels>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReelsBinding.inflate(inflater, container, false)

        adapter = ReelsAdapter(requireContext())
        binding.viewPager.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REELS)
            .get().addOnSuccessListener {
                reelList.clear()
                for (i in it) {
                    val reels = i.toObject<Reels>()
                    reelList.add(reels)
                }
                reelList.reverse()
                adapter.submitList(reelList)
            }

        return binding.root
    }

}