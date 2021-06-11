package com.example.fantansyapp.ui.userDetails

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.data.repositories.UserRepository
import com.example.fantansyapp.databinding.FragmentUserDetailsBinding
import com.example.fantansyapp.utils.snackBar
import kotlinx.coroutines.launch

private const val TAG = "UserDetailsFragment"

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private val binding: FragmentUserDetailsBinding by viewBinding()

    private var bundleUser: User? = null
    private lateinit var user: User


    private val bundleKey = "bundleUserDetails"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()


    }


    private fun initData() {
        bundleUser = requireArguments().get(bundleKey) as User
        user = bundleUser!!


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

            updateButton.setOnClickListener {

                val dataToUpdate = user.copy(
                    name = (binding.name.text.toString()),
                    email = (binding.email.text.toString()),
                    phone = (binding.phone.text.toString()),
                    instagramAccountId = (binding.insta.text.toString()),
                    paymentMethod = (binding.paymentMethod.text.toString()),
                    paymentId = (binding.paymentId.text.toString()),
                    coins = (binding.coins.text.toString()),
                    accountType = (binding.accountType.text.toString()),
                    planPurchaseDate = (binding.planPurchaseDate.text.toString()),
                    plan = (binding.plan.text.toString()),
                    planExpDate = (binding.planExpDate.text.toString())
                )

                updateDetails(dataToUpdate)

            }

            deleteButton.setOnClickListener {
                deleteUser(user.email)
            }

        }


    }

    private fun updateDetails(user: User) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {

                val result = UserRepository(requireContext()).updateSingleUser(user)
                if (result.result == "success") {
                    requireView().snackBar("User Data Updated Successfully")
                }
            } catch (e: Exception) {
                requireView().snackBar(e.localizedMessage.toString())
            }
        }
    }

    private fun deleteUser(email: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {

                val result = UserRepository(requireContext()).deleteUser(email)
                if (result.result == "User Deleted Successfully") {
                    requireView().snackBar("User Deleted Successfully")

                    findNavController().popBackStack()
                }
            } catch (e: Exception) {
                requireView().snackBar(e.localizedMessage.toString())
            }
        }
    }


}