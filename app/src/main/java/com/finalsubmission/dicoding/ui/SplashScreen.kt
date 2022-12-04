package com.finalsubmission.dicoding.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.finalsubmission.dicoding.R
import com.finalsubmission.dicoding.preferences.LoginPref


class SplashScreen : AppCompatActivity() {
    private lateinit var loginPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        loginPref = getSharedPreferences(LoginPref.PREF_NAME, MODE_PRIVATE)
        val name = loginPref.getString(LoginPref.NAME, null)
        if( name != null){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, StoriesActivity::class.java))
                finish()
            }, 2000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }
    }
}