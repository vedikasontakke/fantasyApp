package com.example.fantansyapp.data.repositories

import android.content.Context
import com.example.fantansyapp.data.models.Result
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.data.network.NetworkInterface
import com.example.fantansyapp.data.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(val context: Context) : SafeApiRequest() {

    suspend fun getAllUsers(): ArrayList<User> {
        return withContext(Dispatchers.IO) {
            apiRequest { NetworkInterface.invoke().getAllUsers() }
        }
    }

    suspend fun searchAllUsers(email: String): ArrayList<User> {
        return withContext(Dispatchers.IO) {
            apiRequest { NetworkInterface.invoke().searchUsersByName(email, email) }
        }

    }

    suspend fun getSingleUser(email: String): User {
        return withContext(Dispatchers.IO) {
            apiRequest { NetworkInterface.invoke().getSingleUser(email) }
        }
    }

    suspend fun deleteUser(email: String): Result {
        return withContext(Dispatchers.IO) {
            apiRequest { NetworkInterface.invoke().deleteUser(email) }
        }
    }

    suspend fun updateSingleUser(
        user: User
    ): Result {
        return withContext(Dispatchers.IO) {
            apiRequest {
                NetworkInterface.invoke().updateUserDetails(
                    name = user.name,
                    email = user.email,
                    phone = user.phone,
                    paymentMethod = user.paymentMethod,
                    paymentId = user.paymentId,
                    coins = user.coins.toString(),
                    accountType = user.accountType,
                    planPurchaseDate = user.planPurchaseDate,
                    instagramAccountId = user.instagramAccountId.toString(),
                    plan = user.plan,
                    planExpDate = user.planExpDate,
                )
            }
        }
    }
}
/*name: String,
         email: String,
         phone: String,
         paymentMethod: String,
         paymentId: String,
         coins: Int,
         accountType: String,
         planPurchaseDate: String,
         plan: String,
         planExpDate: String,*/