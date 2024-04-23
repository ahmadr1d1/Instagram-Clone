package com.ahmadrd.instagramclone.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.instagramclone.databinding.FragmentSearchBinding
import com.ahmadrd.instagramclone.models.User
import com.ahmadrd.instagramclone.ui.adapter.SearchAdapter
import com.ahmadrd.instagramclone.utils.Constant.USER
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private val userList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(requireContext())
        binding.rvSearch.adapter = adapter

        binding.searchButton.setOnClickListener {
            val text: String = binding.searchView2.text.toString()

            Firebase.firestore.collection(USER).whereEqualTo("name", text)
                .get().addOnSuccessListener {
                    userList.clear()
                    if (it.isEmpty) {
                        println("")
                    } else {
                        for (i in it.documents) {
                            if (i.id == Firebase.auth.currentUser!!.uid) {
                                println("")
                            } else {
                                val user = i.toObject<User>()!!
                                userList.add(user)
                            }
                        }
                        adapter.submitList(userList)
                    }
                }
        }

        Firebase.firestore.collection(USER).get().addOnSuccessListener {documents ->
            userList.clear()
            for (i in documents) {
                if (i.id == Firebase.auth.currentUser!!.uid) {
                    println("")
                } else {
                    val user = i.toObject<User>()
                    userList.add(user)
                }
            }
            adapter.submitList(userList)
        }

        return binding.root
    }
}