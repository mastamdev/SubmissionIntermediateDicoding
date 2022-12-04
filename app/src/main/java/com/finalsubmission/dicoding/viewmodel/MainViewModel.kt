package com.finalsubmission.dicoding.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finalsubmission.dicoding.api.ApiConfig
import com.finalsubmission.dicoding.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val userResponse = MutableLiveData<UserResponse>()
    val loginResponse = MutableLiveData<LoginResponse>()

    val listStory = MutableLiveData<ArrayList<ItemStoryRespone>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isIntent = MutableLiveData<Boolean>()
    val isIntent: LiveData<Boolean> = _isIntent

    private val _messege = MutableLiveData<String>()
    val messege: LiveData<String> = _messege

    fun register(name: String, email: String, password: String){
        _isLoading.value = true
        val regist = ApiConfig.getApiService().createUser(name, email, password)
        regist.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    userResponse.postValue(response.body())
                    Log.d("Success", response.body().toString())
                    _isIntent.value = true
                    _messege.value = response.body()?.message
                } else{
                    _messege.value = "Email/Password invalid"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun login(email: String, password: String){
        _isLoading.value = true
        val login = ApiConfig.getApiService().loginUser(email, password)
        login.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    loginResponse.postValue(response.body())

                    Log.d("Success", response.body().toString())
                    _isIntent.value = true
                    _messege.value = response.body()?.message
                } else {
                    _messege.value = "Email/Password invalid"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun setData(location: Int, token: String){
        val data = ApiConfig.getApiService().getStoriesMaps(location, token = "Bearer $token")
        data.enqueue(object: Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>,
            ) {
                if (response.isSuccessful){
                    listStory.postValue(response.body()?.listStory)
                    _messege.value = response.body()?.message
                } else {
                    _messege.value = response.message()
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun addFile(imageMultipart: MultipartBody.Part, description: RequestBody, latitude: Double, longitude: Double, token: String) {
        val service = ApiConfig.getApiService().uploadImage(imageMultipart, description, latitude, longitude, token = "Bearer $token")
        service.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _messege.value = response.body()?.message
                    }
                } else {
                    _messege.value = response.message()
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun get(): LiveData<ArrayList<ItemStoryRespone>> = listStory

    fun getLogin(): LiveData<LoginResponse> = loginResponse
}