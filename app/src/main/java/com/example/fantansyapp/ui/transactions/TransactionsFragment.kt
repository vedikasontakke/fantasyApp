package com.example.fantansyapp.ui.transactions

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.Transaction
import com.example.fantansyapp.data.repositories.TransactionRepo
import com.example.fantansyapp.databinding.FragmentTransactionBinding
import com.example.fantansyapp.utils.snackBar
import com.example.fantansyapp.utils.visible

private const val TAG = "TransactionsFragment"
class TransactionsFragment : Fragment(R.layout.fragment_transaction),TransactionAdapter.OnTranactionClickListener {
    private val binding: FragmentTransactionBinding by viewBinding()
    private lateinit var transRepo: TransactionRepo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        getAllTransactions()
    }

    private fun initData() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        transRepo = TransactionRepo(requireContext())

    }

    private fun getAllTransactions() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            binding.progressBar.visible(true)
            try {
                val transactionsList = transRepo.getAllTransaction()

                val mAdapter = TransactionAdapter(transactionsList, this@TransactionsFragment)
                binding.recyclerView.adapter = mAdapter
                binding.progressBar.visible(false)
            } catch (e: Exception) {
                requireView().snackBar(e.stackTraceToString())
                Log.e(TAG, "getAllUser: ${e.printStackTrace()}", )
                binding.progressBar.visible(false)
            }

        }
    }

    override fun clickListener(transaction: Transaction) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            binding.progressBar.visible(true)
            try {
                val result = transRepo.makeTransactionDone(transaction.id)

                if (result.result == "data updated successfully"){
                    getAllTransactions()
                }


            } catch (e: Exception) {
                requireView().snackBar(e.stackTraceToString())
                Log.e(TAG, "getAllUser: ${e.printStackTrace()}", )
                binding.progressBar.visible(false)
            }

        }
    }

}