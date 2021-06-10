package com.example.fantansyapp.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RetrifitInterface {

    @get:GET("getUserData")
    val getUserData : Call<List<userModelClass?>?>?

    companion object{
        const val BASE_URL = "http://localhost:8080/projectsAndroid/earningapi/Welcome/"
    }
}