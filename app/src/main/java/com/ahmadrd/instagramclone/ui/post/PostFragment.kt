package com.ahmadrd.instagramclone.ui.post

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmadrd.instagramclone.databinding.FragmentPostBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PostFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener {
            val intent = Intent(requireContext(), AddPostActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.reels.setOnClickListener {
            val intent = Intent(requireContext(), AddReelsActivity::class.java)
            activity?.startActivity(intent)
        }

        return binding.root
    }
}