package com.finalsubmission.dicoding.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.finalsubmission.dicoding.R
import com.finalsubmission.dicoding.databinding.ActivityRegisterBinding
import com.finalsubmission.dicoding.viewmodel.MainViewModel
import java.lang.StringBuilder

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        playAnimation()
        registerViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.apply {
            btnRegister.setOnClickListener { register() }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvLogin = ObjectAnimator.ofFloat(binding.tviewRegister, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.inputLayoutname, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.inputLayoutemail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.inputLayoutpassword, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(tvLogin, name, email, password, register)
            start()
        }
    }

    private fun register() {
        binding.apply {
            val txtName = etName.text.toString()
            val txtEmail = etextEmail.text.toString()
            val txtPassword = etPassword.text.toString()

            if (txtName.equals(null)){ etName.error = resources.getString(R.string.must_be_filled) }
            if (txtEmail.equals(null)){ etextEmail.error = StringBuilder(resources.getString(R.string.must_be_filled)) }
            if (txtPassword.equals(null)){ etPassword.error = StringBuilder(resources.getString(R.string.must_be_filled)) }

            registerViewModel.register(txtName, txtEmail, txtPassword)

            with(registerViewModel){
                isLoading.observe(this@RegisterActivity) { showLoading(it) }
                isIntent.observe(this@RegisterActivity) { move ->
                    if (move){
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        }, 2000)
                    }
                }
                messege.observe(this@RegisterActivity) { showToast(it) }
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
}