package com.example.fantansyapp.data.network
import com.example.fantansyapp.data.models.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface NetworkInterface {
/*    @FormUrlEncoded
    @POST("sendSMS")
    suspend fun sendOtp(
        @Field("phone") phone: String,
    ): Response<SendOtpResponse>


    @FormUrlEncoded
    @POST("verifyVolCode")
    suspend fun verifyOtp(
        @Field("phone") phone: String,
        @Field("otp") otp: String,
        @Field("token") token: String?,
    ): Response<Volentiere>*/



    @GET("getAllUsers")
    suspend fun getAllUsers(
    ): Response<ArrayList<User>>

    companion object {
        operator fun invoke(
            //  networkConnectionInterceptor: NetworkConnectionInterceptor
        ): NetworkInterface {
            //val Server_url = "https://digitdeveloper.online/langar/tesing/send_otp/welcome/";
             val BASE_URL = "http://localhost:8080/projectsAndroid/earningapi/Welcome/"


            val loginInspector =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(loginInspector)
                //   .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkInterface::class.java)
        }
    }
}