package com.finalsubmission.dicoding.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.finalsubmission.dicoding.api.ApiService
import com.finalsubmission.dicoding.response.ItemStoryRespone

class StoriesRepository(private  val apiService: ApiService, private val token: String) {
    fun getStories(): LiveData<PagingData<ItemStoryRespone>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoriesPagingSource(apiService, token)
            }
        ).liveData
    }
}