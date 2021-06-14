package com.example.fantansyapp.data.repositories

import android.content.Context
import com.example.fantansyapp.data.models.Result
import com.example.fantansyapp.data.models.Transaction
import com.example.fantansyapp.data.network.NetworkInterface
import com.example.fantansyapp.data.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepo(val context: Context) : SafeApiRequest() {

    suspend fun getAllTransaction(type: String): ArrayList<Transaction> {
        return if (type.equals("UNPAID")) {
            withContext(Dispatchers.IO) {
                apiRequest { NetworkInterface.invoke().getAllTransactions() }
            }
        } else if(type.equals("PAID")){
            withContext(Dispatchers.IO) {
                apiRequest { NetworkInterface.invoke().getAllPaidTransactions() }
            }
        }else{
            withContext(Dispatchers.IO) {
                apiRequest { NetworkInterface.invoke().getAllRejectedTransactions() }
            }
        }

    }


    suspend fun makeTransactionDone(id: String): Result {
        return withContext(Dispatchers.IO) {
            apiRequest { NetworkInterface.invoke().makeTractionCompleted(id) }
        }
    }

    suspend fun denyTransaction(id: String): Result {
        return withContext(Dispatchers.IO) {
            apiRequest { NetworkInterface.invoke().denyTransaction(id) }
        }
    }


}

