package com.example.fantansyapp.ui.transactionDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.fantansyapp.R
import com.example.fantansyapp.data.repositories.TransactionRepo
import com.example.fantansyapp.data.repositories.UserRepository
import com.example.fantansyapp.databinding.FragmentTransactionDetailsBinding
import com.example.fantansyapp.utils.snackBar
import kotlinx.android.synthetic.main.fragment_transaction_details.*
import kotlinx.coroutines.launch

private const val TAG = "TransactionDetailsFragm"
class TransactionDetailsFragment: Fragment(R.layout.fragment_transaction_details) {

    val binding:FragmentTransactionDetailsBinding by viewBinding()
    private lateinit var userEmail:String
    private lateinit var transactionId:String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        getUserByEmail()

    }

    private fun getUserByEmail() {
        viewLifecycleOwner.lifecycleScope.launch {
            //show progress bar
            try {
                val user = UserRepository(requireContext()).getSingleUser(userEmail)


                binding.apply {

                    Glide.with(requireView()).load(user.getImage).into(profileImage)
                    email.text = user.email
                    name.setText(user.name)
                    phone.setText(user.phone)
                    insta.setText(user.instagramAccountId)
                    paymentMethod.setText(user.paymentMethod)
                    paymentId.setText(user.paymentId)
                    coins.setText(user.coins.toString())
                    accountType.setText(user.accountType)
                    planPurchaseDate.setText(user.planPurchaseDate)
                    plan.setText(user.plan)
                    planExpDate.setText(user.planExpDate)
                }

                doneButton.setOnClickListener {
                    makeTransactionDone()
                }



            } catch (e: Exception) {
                requireView().snackBar(e.message.toString())
            }


        }
    }

    private fun initData() {
        val bundleEmail = requireArguments().getString("bundleEmail")
        val bundleTid = requireArguments().getString("BundelTransactonId")
        userEmail = bundleEmail!!
        transactionId = bundleTid!!
    }

    private fun makeTransactionDone(){
      viewLifecycleOwner.lifecycleScope.launchWhenStarted {

          //binding.progressBar.visible(true)
          try {
              val result = TransactionRepo(requireContext()).makeTransactionDone(transactionId)

              if (result.result == "data updated successfully"){
                 findNavController().popBackStack()
              }


          } catch (e: Exception) {
              requireView().snackBar(e.stackTraceToString())
              Log.e(TAG, "getAllUser: ${e.printStackTrace()}", )
             // binding.progressBar.visible(false)
          }

      }
    }
}