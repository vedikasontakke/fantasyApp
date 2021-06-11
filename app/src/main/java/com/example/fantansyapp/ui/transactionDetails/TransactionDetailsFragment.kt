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
import com.example.fantansyapp.utils.visible
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
            binding.progressBarTransitionDetail.visible(true)

            try {
                val user = UserRepository(requireContext()).getSingleUser(userEmail)


                binding.apply {

                    Glide.with(requireView()).load(user.getImage).into(profileImage)
                    email.text = user.email
                    name.text = user.name
                    phone.text = user.phone
                    insta.text = user.instagramAccountId
                    paymentMethod.text = user.paymentMethod
                    paymentId.text = user.paymentId
                    coins.text = user.coins.toString()
                    accountType.text = user.accountType
                    planPurchaseDate.text = user.planPurchaseDate
                    plan.text = user.plan
                    planExpDate.text = user.planExpDate
                }

                doneButton.setOnClickListener {
                    makeTransactionDone()
                }

                binding.progressBarTransitionDetail.visible(false)


            } catch (e: Exception) {
                requireView().snackBar(e.message.toString())

                binding.progressBarTransitionDetail.visible(false)

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

          binding.progressBarTransitionDetail.visible(true)
          try {
              val result = TransactionRepo(requireContext()).makeTransactionDone(transactionId)

              if (result.result == "data updated successfully"){
                 findNavController().popBackStack()
              }

              binding.progressBarTransitionDetail.visible(false)


          } catch (e: Exception) {
              requireView().snackBar(e.stackTraceToString())
              Log.e(TAG, "getAllUser: ${e.printStackTrace()}", )
              binding.progressBarTransitionDetail.visible(false)
          }

      }
    }
}