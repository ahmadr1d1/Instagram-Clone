package com.ahmadrd.instagramclone.ui.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ahmadrd.instagramclone.databinding.ActivityAddPostBinding
import com.ahmadrd.instagramclone.models.Post
import com.ahmadrd.instagramclone.ui.launcher.HomeActivity
import com.ahmadrd.instagramclone.utils.Constant.POST
import com.ahmadrd.instagramclone.utils.Constant.POST_FOLDER
import com.ahmadrd.instagramclone.utils.Constant.USER
import com.ahmadrd.instagramclone.utils.Util
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    private var imageUrl: String? = null
    private val util = Util()
    companion object {
        private const val TAG = "AddPostActivity"
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            util.uploadVideo(uri, POST_FOLDER, this) { url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back toolbar autogenerate
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.btnPost.setOnClickListener {
            Firebase.firestore.collection(USER).document(Firebase.auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {
                    val post = Post(
                        postUrl = imageUrl!!,
                        caption = binding.etCaption.editText?.text.toString(),
                        uid =  Firebase.auth.currentUser!!.uid,
                        time = System.currentTimeMillis().toString()
                    )

                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post)
                            .addOnSuccessListener {
                                Toast.makeText(this@AddPostActivity, "Success add post", Toast.LENGTH_SHORT)
                                    .show()

                                val intent = Intent(this@AddPostActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Error getting documents: ", exception)
                    }
                }
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent(this@AddPostActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}