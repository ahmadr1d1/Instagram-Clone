package com.ahmadrd.instagramclone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.FragmentProfileBinding
import com.ahmadrd.instagramclone.models.User
import com.ahmadrd.instagramclone.ui.adapter.ViewPagerAdapter
import com.ahmadrd.instagramclone.ui.bottomsheet.MoreProfile
import com.ahmadrd.instagramclone.ui.launcher.RegisterActivity
import com.ahmadrd.instagramclone.ui.post.AddPostActivity
import com.ahmadrd.instagramclone.utils.Constant.USER
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    companion object {
        const val EDIT_PROFILE = "Edit"
        const val MY_POST = "My Post"
        const val MY_REELS = "My Reels"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.putExtra(EDIT_PROFILE, 1)
            activity?.startActivity(intent)
        }

        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragments(MyPostFragment(), MY_POST)
        viewPagerAdapter.addFragments(MyReelsFragment(), MY_REELS)

        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.toolbarProfile.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.postProfile -> {
                    val intent = Intent(requireContext(), AddPostActivity::class.java)
                    activity?.startActivity(intent)
                }
                R.id.menuProfile -> {
                    val bottomSheetDialogFragment = MoreProfile()
                    bottomSheetDialogFragment.show(parentFragmentManager, "Hamburger")
                }
            }
            true
        }

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user: User = it.toObject<User>()!!
                binding.name.text = user.name
                binding.bio.text = user.email

                if(!user.image.isNullOrEmpty()) {
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }
}