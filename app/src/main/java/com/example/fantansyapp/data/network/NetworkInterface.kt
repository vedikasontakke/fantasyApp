package com.example.fantansyapp.data.network
import com.example.fantansyapp.data.models.Result
import com.example.fantansyapp.data.models.Transaction
import com.example.fantansyapp.data.models.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


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

    @GET("getUserByEmail")
    suspend fun getSingleUser(
            @Query("email")email:String
    ): Response<User>

    @GET("getAllUnpaidTransactions")
    suspend fun getAllTransactions(
    ): Response<ArrayList<Transaction>>

    @GET("makeTransactionDone")
    suspend fun makeTractionCompleted(
            @Query("transactionId")id:String
    ): Response<Result>

    @FormUrlEncoded
    @POST("searchUsersByEmail")
    suspend fun searchUsersByName(
            @Field("email")email: String,
            @Field("name")name:String
    ): Response<ArrayList<User>>



    companion object {
        operator fun invoke(
            //  networkConnectionInterceptor: NetworkConnectionInterceptor
        ): NetworkInterface {
            //vedika
             //val BASE_URL = "http://192.168.43.227:8080/projectsAndroid/earningapi/Welcome/"

            //parth
            val BASE_URL = "http://192.168.43.146/Learing/earning_app/Welcome/"

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