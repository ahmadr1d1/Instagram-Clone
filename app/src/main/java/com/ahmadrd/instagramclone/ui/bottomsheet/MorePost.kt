package com.ahmadrd.instagramclone.ui.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahmadrd.instagramclone.databinding.FragmentMorePostBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MorePost : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMorePostBinding
    companion object {
        const val KEY = "key"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMorePostBinding.inflate(inflater, container, false)

        val data = arguments?.getString(KEY)
        Log.d("MorePost", "Get $data")

        binding.imgSave.setOnClickListener {
            Toast.makeText(requireContext(), "Save CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.imgQRCode.setOnClickListener {
            Toast.makeText(requireContext(), "QRCode CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.send.setOnClickListener {
            Toast.makeText(requireContext(), "send CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.unfollow.setOnClickListener {
            Toast.makeText(requireContext(), "unfollow CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.about.setOnClickListener {
            Toast.makeText(requireContext(), "about CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.report.setOnClickListener {
            Toast.makeText(requireContext(), "report CLicked", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}