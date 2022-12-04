package com.finalsubmission.dicoding.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.finalsubmission.dicoding.data.StoriesRepository
import com.finalsubmission.dicoding.di.Injection
import com.finalsubmission.dicoding.response.ItemStoryRespone

class StoriesViewModel(storiesRepository: StoriesRepository) : ViewModel() {
    val stories: LiveData<PagingData<ItemStoryRespone>> =
        storiesRepository.getStories().cachedIn(viewModelScope)
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoriesViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}