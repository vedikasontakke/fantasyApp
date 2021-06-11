package com.example.fantansyapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
        val id:String,
        val coins:String,
        val date:String,
        val email:String,
        val status:String,
        val plan:String
):Parcelable