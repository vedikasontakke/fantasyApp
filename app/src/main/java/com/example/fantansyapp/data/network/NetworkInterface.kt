package com.example.fantansyapp.data.network

import com.example.fantansyapp.data.models.*
import com.example.fantansyapp.utils.Constants.Companion.BASE_URL
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface NetworkInterface {

    //add New Book
    @Multipart
    @POST("addImageToSlider")
    suspend fun addImageToSlider(
            @Part bookImage: MultipartBody.Part,
            @Part("link") link: RequestBody,

            ): Response<Result>


    @GET("getAllSliderImages")
    suspend fun getAllSliderImages(

    ): Response<ArrayList<Slider>>

    @FormUrlEncoded
    @POST("deleteImageFromSlider")
    suspend fun deleteImageFromSlider(
            @Field("id") sliderId: String,
    ): Response<Result>


    @GET("getHistoryByGmail")
    suspend fun getHistoryByGmail(
            @Query("email") email: String
    ): Response<ArrayList<History>>


    @GET("getAllUsers")
    suspend fun getAllUsers(
    ): Response<ArrayList<User>>

    @GET("getAllUserAccordingToCoins")
    suspend fun getAllUserAccordingToCoins(
    ): Response<ArrayList<User>>

    @GET("getUserByEmail")
    suspend fun getSingleUser(
            @Query("email") email: String
    ): Response<User>

    @GET("deleteUser")
    suspend fun deleteUser(
            @Query("email") email: String
    ): Response<Result>

    @GET("getAllUnpaidTransactions")
    suspend fun getAllTransactions(
    ): Response<ArrayList<Transaction>>

    @GET("getAllpaidTransactions")
    suspend fun getAllPaidTransactions(
    ): Response<ArrayList<Transaction>>

    @GET("getAllRejectedTransactions")
    suspend fun getAllRejectedTransactions(
    ): Response<ArrayList<Transaction>>

    @GET("makeTransactionDone")
    suspend fun makeTractionCompleted(
            @Query("transactionId") id: String
    ): Response<Result>

    @GET("denyTransaction")
    suspend fun denyTransaction(
            @Query("transactionId") id: String
    ): Response<Result>

    @FormUrlEncoded
    @POST("searchUsersByEmail")
    suspend fun searchUsersByName(
            @Field("email") email: String,
            @Field("name") name: String
    ): Response<ArrayList<User>>



    @FormUrlEncoded
    @POST("updateUserDetails")
    suspend fun updateUserDetails(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("phone") phone: String,
            @Field("paymentMethod") paymentMethod: String,
            @Field("paymentId") paymentId: String,
            @Field("coins") coins: String,
            @Field("accountType") accountType: String,
            @Field("planPurchaseDate") planPurchaseDate: String,
            @Field("instagramAccountId") instagramAccountId: String,
            @Field("plan") plan: String,
            @Field("planExpDate") planExpDate: String
    ): Response<Result>

    @GET("getAllLimits")
    suspend fun getLimits(): Response<Limit>

    @FormUrlEncoded
    @POST("updateLimits")
    suspend fun updateLimits(
            @Field("scratch") scratch: String,
            @Field("refer") refer: String,
            @Field("guess") guess: String,
            @Field("spinner") spinner: String
    ): Response<Result>

    companion object {
        operator fun invoke(
                //  networkConnectionInterceptor: NetworkConnectionInterceptor
        ): NetworkInterface {

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