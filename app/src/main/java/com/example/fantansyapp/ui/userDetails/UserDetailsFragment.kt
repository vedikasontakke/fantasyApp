package com.example.fantansyapp.ui.userDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.databinding.FragmentUserDetailsBinding

private const val TAG = "UserDetailsFragment"
class UserDetailsFragment: Fragment() {

    private val binding:FragmentUserDetailsBinding by viewBinding()

    private var bundleUser:User? = null
    private lateinit var user:User


    private val bundleKey = "bundleUserDetails"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        logData()
    }

    private fun logData() {
        val logData = """
           name = ${user.name}
           email = ${user.email}
        """
        Log.e(TAG, "logData: $logData")
    }

    private fun initData() {
        bundleUser = requireArguments().get(bundleKey) as User
        user = bundleUser!!
    }
}