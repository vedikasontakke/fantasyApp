package com.example.fantansyapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fantansyapp.R
import com.example.fantansyapp.data.reoositiries.UserReposeitory
import com.example.fantansyapp.databinding.FragmentHomeBinding
import com.example.fantansyapp.utils.snackBar

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    private val binding:FragmentHomeBinding by viewBinding()
    private lateinit var userRepo:UserReposeitory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        getAllUser()
    }

    private fun initData() {
        userRepo = UserReposeitory(requireContext())
    }

    private fun getAllUser() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            try {
                val usersList = userRepo.getAllUsers()

                for(user in usersList){
                    Log.e(TAG, "getAllUser: ${user.name}" )
                }

            }catch (e:Exception){
                requireView().snackBar(e.toString())
            }

        }
    }
}