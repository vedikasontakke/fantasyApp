package com.example.fantansyapp.data.reoositiries

import android.R
import android.content.ContentValues.TAG
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.fantansyapp.BuildConfig
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.data.network.NetworkInterface
import com.example.fantansyapp.data.network.SafeApiRequest
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserReposeitory(val context: Context):SafeApiRequest() {

    suspend fun getAllUsers():ArrayList<User>{
        return withContext(Dispatchers.IO){
            apiRequest {NetworkInterface.invoke().getAllUsers()}
        }
    }


}