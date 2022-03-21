package com.example.moduleautocomtext.Model

import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET("users")
    fun getUserList(): Call<List<UserModel>>
}