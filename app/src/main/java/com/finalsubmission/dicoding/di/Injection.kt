package com.finalsubmission.dicoding.di

import android.content.Context
import com.finalsubmission.dicoding.api.ApiConfig
import com.finalsubmission.dicoding.preferences.LoginPref
import com.finalsubmission.dicoding.data.StoriesRepository

object Injection {
    fun provideRepository(context: Context): StoriesRepository {
        val apiService = ApiConfig.getApiService()
        val token = LoginPref(context).getUser().token.toString()
        return StoriesRepository(apiService, token)
    }
}
