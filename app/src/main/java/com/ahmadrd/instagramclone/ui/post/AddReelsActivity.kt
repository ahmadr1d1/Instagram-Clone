package com.ahmadrd.instagramclone.ui.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ahmadrd.instagramclone.databinding.ActivityAddReelsBinding
import com.ahmadrd.instagramclone.models.Reels
import com.ahmadrd.instagramclone.models.User
import com.ahmadrd.instagramclone.ui.launcher.HomeActivity
import com.ahmadrd.instagramclone.utils.Constant.REELS
import com.ahmadrd.instagramclone.utils.Constant.REELS_FOLDER
import com.ahmadrd.instagramclone.utils.Constant.USER
import com.ahmadrd.instagramclone.utils.Util
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class AddReelsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReelsBinding
    private val util = Util()
    companion object {
        private const val TAG = "AddReelsActivity"
    }

    private lateinit var videoUrl: String
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            util.uploadVideo(uri, REELS_FOLDER, this){ url ->
                if (url != null) {
                    videoUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Back toolbar autogenerate
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.selectReels.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.btnPost.setOnClickListener {

            Firebase.firestore.collection(USER).document(Firebase.auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {
                    val user: User = it.toObject<User>()!!
                    val reels = Reels(videoUrl, binding.etCaption.editText?.text.toString(), user.image!!)

                    Firebase.firestore.collection(REELS).document().set(reels).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REELS)
                            .document()
                            .set(reels)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this@AddReelsActivity, "Success add post",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@AddReelsActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { exception ->
                                // Handle error jika terjadi
                                Toast.makeText(this,
                                    "Error fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
                                Log.e(TAG, "Error getting documents: ", exception)
                            }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this,
                            "Error fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error getting documents: ", exception)
                    }
                }
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent(this@AddReelsActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}