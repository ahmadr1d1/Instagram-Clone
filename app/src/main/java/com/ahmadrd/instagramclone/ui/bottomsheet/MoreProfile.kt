package com.ahmadrd.instagramclone.ui.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahmadrd.instagramclone.databinding.FragmentMoreProfileBinding
import com.ahmadrd.instagramclone.ui.launcher.LoginActivity
import com.ahmadrd.instagramclone.ui.profile.SettingsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth


class MoreProfile : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMoreProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreProfileBinding.inflate(inflater, container, false)

        binding.settings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.activity.setOnClickListener {
            Toast.makeText(requireContext(), "your activity CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.archive.setOnClickListener {
            Toast.makeText(requireContext(), "archive CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.qrCode.setOnClickListener {
            Toast.makeText(requireContext(), "QR Code CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.saved.setOnClickListener {
            Toast.makeText(requireContext(), "saved CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.closeFriends.setOnClickListener {
            Toast.makeText(requireContext(), "closeFriends CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.flipside.setOnClickListener {
            Toast.makeText(requireContext(), "flipSide CLicked", Toast.LENGTH_SHORT).show()
        }

        binding.logout.setOnClickListener {
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            activity?.startActivity(intent)
        }

        return binding.root
    }
}