package com.example.fantansyapp.data.repositories

import android.content.Context
import com.example.fantansyapp.data.models.Limit
import com.example.fantansyapp.data.models.Result
import com.example.fantansyapp.data.network.NetworkInterface
import com.example.fantansyapp.data.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class LimitsRepository(val context: Context): SafeApiRequest() {

    suspend fun getLimits():Limit {
        return withContext(Dispatchers.IO){
            apiRequest { NetworkInterface.invoke().getLimits()}
        }
    }

    suspend fun updateLimits(limit: Limit): Result {
        return withContext(Dispatchers.IO){
            apiRequest { NetworkInterface.invoke().updateLimits(
                    scratch = limit.scratch,
                    guess = limit.guess,
                    spinner = limit.spinner,
                    refer = limit.refer)}
        }
    }

    suspend fun uploadSliderImage(body: MultipartBody.Part):Result {
        return withContext(Dispatchers.IO){
            apiRequest { NetworkInterface.invoke().addImageToSlider(body)}
        }
    }

}