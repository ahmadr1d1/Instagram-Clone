package com.ahmadrd.instagramclone.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.ahmadrd.instagramclone.R
import com.ahmadrd.instagramclone.utils.SettingPreferences
import com.ahmadrd.instagramclone.utils.SettingViewModelFactory
import com.ahmadrd.instagramclone.utils.dataStore
import com.ahmadrd.instagramclone.viewmodel.SettingThemeViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.post {
            if (FirebaseAuth.getInstance().currentUser == null) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                val options = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.fade_in, R.anim.scale_down)
                ActivityCompat.startActivity(this, intent, options.toBundle())
                finish()
            } else {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                val options = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.fade_in, R.anim.scale_down)
                ActivityCompat.startActivity(this, intent, options.toBundle())
                finish()
            }
        }

        // For Dark Theme Const
        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val themeViewModel =
            ViewModelProvider(this,
                SettingViewModelFactory(settingPreferences))[SettingThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}