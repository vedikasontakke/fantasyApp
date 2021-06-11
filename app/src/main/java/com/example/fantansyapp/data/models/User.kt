package com.example.fantansyapp.data.models

import android.os.Parcelable
import com.example.fantansyapp.utils.Constants.Companion.IMAGE_URL
import kotlinx.android.parcel.Parcelize
@Parcelize
data class User(
        val id: Int = 0,
        val name: String,
        val email: String,
        val phone: String,
        val password: String,
        val dob: String,
        val gender: String,
        val paymentMethod: String,
        val paymentId: String,
        val profileImage: String,
        val coins: Int,
        val accountType: String,
        val planPurchaseDate: String,
        val yourFriendSponserId: String?,
        val instagramAccountId: String?,
        val deviceId: String?,
        val mySponserId: String,
        val plan: String,
        val planExpDate: String,
) : Parcelable {

    val getImage get() = "http://192.168.43.146/Learing/earning_app/profiles/"+profileImage

}


