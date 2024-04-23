package com.ahmadrd.instagramclone.ui.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.databinding.ActivityLoginBinding
import com.ahmadrd.instagramclone.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if((binding.etEmail.editText?.text.toString() == "") or
                (binding.etPassword.editText?.text.toString() == "")
            ) {
                Toast.makeText(this@LoginActivity, R.string.blank, Toast.LENGTH_SHORT).show()
            } else {
                val user = User(binding.etEmail.editText?.text.toString(),
                    binding.etPassword.editText?.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity,
                                R.string.loginIncorrect, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}