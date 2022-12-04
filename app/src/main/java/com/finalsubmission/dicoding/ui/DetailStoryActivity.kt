package com.finalsubmission.dicoding.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.finalsubmission.dicoding.R
import com.finalsubmission.dicoding.databinding.ActivityDetailStoryBinding
import com.finalsubmission.dicoding.preferences.LoginPref
import com.finalsubmission.dicoding.utils.DateFormatter
import com.finalsubmission.dicoding.viewmodel.MainViewModel
import java.util.*

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var detailViewModel: MainViewModel

    private lateinit var mLoginPreference: LoginPref

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.detail_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mLoginPreference = LoginPref(this)

        getDetail()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDetail(){
        val name = intent.getStringExtra(NAME)
        val photo = intent.getStringExtra(PHOTOURL)
        val desc = intent.getStringExtra(DESCRIPTION)
        val date = intent.getStringExtra(DATE)
        binding.apply {
            tviewName.text = name
            tviewDescription.text = desc
            tvDate.text = DateFormatter.formatDate(date.toString(), TimeZone.getDefault().id)
            Glide.with(this@DetailStoryActivity)
                .load(photo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivStories)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val NAME = "name"
        const val PHOTOURL = "link"
        const val DESCRIPTION = "descripsi"
        const val DATE = "date"
    }
}
