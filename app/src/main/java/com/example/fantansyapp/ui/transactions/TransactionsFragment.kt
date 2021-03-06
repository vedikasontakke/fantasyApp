package com.example.fantansyapp.ui.transactions

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.Transaction
import com.example.fantansyapp.data.repositories.TransactionRepo
import com.example.fantansyapp.databinding.FragmentTransactionBinding
import com.example.fantansyapp.ui.user.UsersFragmentDirections
import com.example.fantansyapp.utils.snackBar
import com.example.fantansyapp.utils.visible

private const val TAG = "TransactionsFragment"
class TransactionsFragment : Fragment(R.layout.fragment_transaction),TransactionAdapter.OnTranactionClickListener {
    private val binding: FragmentTransactionBinding by viewBinding()
    private lateinit var transRepo: TransactionRepo
    var paymentStatus:String = "UNPAID"

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

        binding.paymentStatus.setItems(listOf("UNPAID","PAID","REJECTED"))
        binding.paymentStatus.setOnItemSelectedListener { _, _, _, item ->
            paymentStatus = item.toString()
            getAllTransactions()
        }

        transRepo = TransactionRepo(requireContext())

    }

    private fun getAllTransactions() {

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {

                binding.progressBarTransition.visible(true)
                try {



                    var transactionsList = transRepo.getAllTransaction(paymentStatus)


                    val mAdapter = TransactionAdapter(transactionsList, this@TransactionsFragment)
                    binding.recyclerView.adapter = mAdapter
                    binding.progressBarTransition.visible(false)
                } catch (e: Exception) {
                    binding.root.snackBar(e.stackTraceToString())
                    Log.e(TAG, "getAllUser: ${e.printStackTrace()}", )
                    binding.progressBarTransition.visible(false)
                }

            }


    }

    override fun clickListener(transaction: Transaction) {
        if (findNavController().currentDestination?.id == R.id.navigation_transactions) {
            TransactionsFragmentDirections.actionNavigationTransactionsToTransactionDetailsFragment(transaction.email,transaction.id,paymentStatus).apply {
                findNavController().navigate(this)
            }
        }


    }

}