package com.finalsubmission.dicoding.api

import com.finalsubmission.dicoding.response.LoginResponse
import com.finalsubmission.dicoding.response.StoriesResponse
import com.finalsubmission.dicoding.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun createUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") token: String
    ): StoriesResponse

    @GET("stories")
    fun getStoriesMaps(
        @Query("location") location: Int,
        @Header("Authorization") token: String
    ): Call<StoriesResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat")lat: Double,
        @Part("lon")lon: Double,
        @Header("Authorization") token: String
    ): Call<UserResponse>
}