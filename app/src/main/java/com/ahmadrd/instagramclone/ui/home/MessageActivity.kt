package com.ahmadrd.instagramclone.ui.home

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.ActivityMessageBinding
import com.ahmadrd.instagramclone.models.User
import com.ahmadrd.instagramclone.ui.adapter.StoryFollowAdapter
import com.ahmadrd.instagramclone.utils.Constant
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding
    private lateinit var followAdapter: StoryFollowAdapter
    private val listFollow = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back toolbar autogenerate
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        toolbar.showOverflowMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.rvStoryFollow.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        followAdapter = StoryFollowAdapter(this)
        binding.rvStoryFollow.adapter = followAdapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + Constant.FOLLOW)
            .get().addOnSuccessListener {
                listFollow.clear()
                for (i in it) {
                    val user = i.toObject<User>()
                    listFollow.add(user)
                }
                followAdapter.submitList(listFollow)
            }

        // SearchBar
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchBar.text.toString()
                    searchView.hide()
                    Toast.makeText(this@MessageActivity, "Searching " + searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.channel -> {
                    Toast.makeText(this, "Channel CLicked", Toast.LENGTH_SHORT).show()
                }
                R.id.newMessage -> {
                    Toast.makeText(this, "newMessage CLicked", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.messages_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}