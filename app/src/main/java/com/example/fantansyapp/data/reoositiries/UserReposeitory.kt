package com.example.fantansyapp.data.reoositiries

import android.content.Context
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.data.network.NetworkInterface
import com.example.fantansyapp.data.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserReposeitory(val context: Context):SafeApiRequest() {

    suspend fun getAllUsers():ArrayList<User>{
        return withContext(Dispatchers.IO){
            apiRequest {NetworkInterface.invoke().getAllUsers()}
        }
    }
}