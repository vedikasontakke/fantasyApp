package com.example.fantansyapp.ui.history

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.History
import com.example.fantansyapp.data.repositories.UserRepository
import com.example.fantansyapp.databinding.FragmentHistoryBinding
import com.example.fantansyapp.utils.snackBar
import com.example.fantansyapp.utils.visible

class HistoryFragment: Fragment(R.layout.fragment_history) {
    val binding:FragmentHistoryBinding by viewBinding()

    private lateinit var userEmail:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

    }



    private fun initData() {
        val bundleEmail = requireArguments().getString("bundleEmail")

        userEmail = bundleEmail!!

        getAllHistory()

    }

    private fun getAllHistory() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            binding.progressBarTransition.visible(true)
            try {

                var transactionsList:ArrayList<History> = UserRepository(requireContext()).getHistoryByGmail(userEmail)


                val mAdapter = HistoryAdapter(transactionsList)
                binding.recyclerView.adapter = mAdapter

                binding.progressBarTransition.visible(false)
            } catch (e: Exception) {
                binding.root.snackBar(e.stackTraceToString())

                binding.progressBarTransition.visible(false)
            }

        }


    }
}