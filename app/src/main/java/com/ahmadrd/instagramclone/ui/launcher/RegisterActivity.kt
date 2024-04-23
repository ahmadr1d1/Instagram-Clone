package com.ahmadrd.instagramclone.ui.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.ActivityRegisterBinding
import com.ahmadrd.instagramclone.models.User
import com.ahmadrd.instagramclone.ui.profile.ProfileFragment.Companion.EDIT_PROFILE
import com.ahmadrd.instagramclone.utils.Constant.USER
import com.ahmadrd.instagramclone.utils.Constant.USER_PROFILE_FOLDER
import com.ahmadrd.instagramclone.utils.Util
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class RegisterActivity : AppCompatActivity() {

    companion object {
        const val UPDATE_PROFILE = "Update Profile"
        private const val TAG = "RegisterActivity"
    }

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var user: User
    private val util = Util()
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            util.uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it != null) {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = User()

        // Update
        if (intent.hasExtra(EDIT_PROFILE)) {
            if (intent.getIntExtra(EDIT_PROFILE, -1) == 1) {
                binding.btnRegister.text = UPDATE_PROFILE
                Firebase.firestore.collection(USER).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if(!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }

                        binding.etName.editText?.setText(user.name)
                        binding.etEmail.editText?.setText(user.email)
                        binding.etPassword.editText?.setText(user.password)
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this,
                            "Error fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error getting documents: ", exception)
                    }
            }
        }

        binding.btnRegister.setOnClickListener {
            if (intent.hasExtra(EDIT_PROFILE)) {
                if (intent.getIntExtra(EDIT_PROFILE, -1) == 1) {
                    Firebase.firestore.collection(USER)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            val intent =
                                Intent(this@RegisterActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this,
                                "Error fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "Error getting documents: ", exception)
                        }
                }
            // Register
            } else {
                if ((binding.etName.editText?.text.toString() == "") or
                    (binding.etEmail.editText?.text.toString() == "") or
                    (binding.etPassword.editText?.text.toString() == "")
                ) {
                    Toast.makeText(this@RegisterActivity, R.string.blank, Toast.LENGTH_SHORT).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.etEmail.editText?.text.toString(),
                        binding.etPassword.editText?.text.toString()
                    ).addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            user.name = binding.etName.editText?.text.toString()
                            user.email = binding.etEmail.editText?.text.toString()
                            user.password = binding.etPassword.editText?.text.toString()

                            Toast.makeText(
                                this@RegisterActivity, R.string.success,
                                Toast.LENGTH_SHORT
                            ).show()

                            // Move to home
                            Firebase.firestore.collection(USER)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    val intent =
                                        Intent(this@RegisterActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                result.exception?.localizedMessage, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.login.setOnClickListener {
            val intent =  Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}