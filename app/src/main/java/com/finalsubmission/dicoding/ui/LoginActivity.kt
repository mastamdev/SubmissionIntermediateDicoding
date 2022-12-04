package com.finalsubmission.dicoding.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.finalsubmission.dicoding.R
import com.finalsubmission.dicoding.databinding.ActivityLoginBinding
import com.finalsubmission.dicoding.preferences.LoginPref
import com.finalsubmission.dicoding.viewmodel.MainViewModel
import com.finalsubmission.dicoding.viewmodel.StoriesViewModel
import com.finalsubmission.dicoding.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: MainViewModel
    private val storiesViewModel: StoriesViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        playAnimation()
        loginViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {
            btnLogin.setOnClickListener { login() }
            tviewRegister.setOnClickListener { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }
        }
    }
    private fun login() {
        binding.apply {
            val txtEmail = etEmail.text.toString()
            val txtPassword = etPassword.text.toString()
            if (txtEmail.isEmpty()){
                etEmail.error = resources.getString(R.string.must_be_filled)
                return
            }
            if (txtPassword.isEmpty()){
                etPassword.error = resources.getString(R.string.must_be_filled)
                return
            }
            with(loginViewModel) {
                isLoading.observe(this@LoginActivity) { showLoading(it) }
                messege.observe(this@LoginActivity) { showToast(it) }
                login(txtEmail, txtPassword)
                getLogin().observe(this@LoginActivity) {
                    val loginpref = LoginPref(this@LoginActivity)
                    val name = it.loginResult.name
                    val userId = it.loginResult.userId
                    val token = it.loginResult.token
                    loginpref.setUser(name, userId, token)
                }
                isIntent.observe(this@LoginActivity) { startActivity(Intent(this@LoginActivity, StoriesActivity::class.java)) }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state : Boolean){
        binding.bars.visibility = if(state) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.iconLogo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvLogin = ObjectAnimator.ofFloat(binding.tviewLogin, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.inputLayout1, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.inputLayout2, View.ALPHA, 1f).setDuration(500)
        val ask = ObjectAnimator.ofFloat(binding.tviewAsk, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.tviewRegister, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(ask, register)
        }

        AnimatorSet().apply {
            playSequentially(tvLogin, email, password, together, signup)
            start()
        }
    }
}