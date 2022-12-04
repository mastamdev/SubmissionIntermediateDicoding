package com.finalsubmission.dicoding.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalsubmission.dicoding.AdapterLoadingState
import com.finalsubmission.dicoding.R
import com.finalsubmission.dicoding.adapter.AdapterStory
import com.finalsubmission.dicoding.databinding.ActivityStoriesBinding
import com.finalsubmission.dicoding.preferences.LoginPref
import com.finalsubmission.dicoding.viewmodel.StoriesViewModel
import com.finalsubmission.dicoding.viewmodel.ViewModelFactory
import kotlin.system.exitProcess

class StoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoriesBinding
    private val storiesViewModel: StoriesViewModel by viewModels {
        ViewModelFactory(this)
    }
    private var backPress: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.story)

        binding.rvStoryview.layoutManager = LinearLayoutManager(this)
        getData()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this@StoriesActivity, AddStoriesActivity::class.java))
        }
    }

    private fun getData(){
        showLoading(true)
        val adapter = AdapterStory()
        binding.rvStoryview.adapter = adapter.withLoadStateFooter(
            footer = AdapterLoadingState {
                adapter.retry()
            }
        )
        storiesViewModel.stories.observe(this, {
            showLoading(false)
            adapter.submitData(lifecycle, it)
        })
    }
    override fun onBackPressed() {
        if (backPress + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finishAffinity()
            exitProcess(0)
        } else {
            showToast(resources.getString(R.string.double_press_to_exit))
        }

        backPress = System.currentTimeMillis()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
            }
            R.id.ic_logout -> {
                showToast(resources.getString(R.string.logout))
                val loginPref = LoginPref(this)
                loginPref.delUser()
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 2000)
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state : Boolean){
        binding.bar.visibility = if(state) View.VISIBLE else View.GONE
    }
}