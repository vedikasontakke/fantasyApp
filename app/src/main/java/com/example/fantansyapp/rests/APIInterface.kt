package com.example.coronadetials.rests

import com.example.coronadetials.model.ResponseModel
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("summary")

    fun getLatestSummary(): Call<ResponseModel?>?

}