package com.example.fantansyapp.data.repositories

import android.content.Context
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.data.network.NetworkInterface
import com.example.fantansyapp.data.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(val context: Context):SafeApiRequest() {

    suspend fun getAllUsers():ArrayList<User>{
        return withContext(Dispatchers.IO){
            apiRequest {NetworkInterface.invoke().getAllUsers()}
        }
    }
    suspend fun searchAllUsers(email: String):ArrayList<User>{
        return withContext(Dispatchers.IO){
            apiRequest {NetworkInterface.invoke().searchUsersByName(email, email)}
        }

    }
    suspend fun getSingleUser(email:String):User{
        return withContext(Dispatchers.IO){
            apiRequest {NetworkInterface.invoke().getSingleUser(email)}
        }
    }
}