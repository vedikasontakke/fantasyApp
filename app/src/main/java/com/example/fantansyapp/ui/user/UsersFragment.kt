package com.example.fantansyapp.ui.user

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.data.repositories.UserRepository
import com.example.fantansyapp.databinding.FragmentUserBinding
import com.example.fantansyapp.utils.searchQuery
import com.example.fantansyapp.utils.snackBar
import com.example.fantansyapp.utils.visible
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

class UsersFragment : Fragment(R.layout.fragment_user), UsersAdapter.OnUserClickListener {

    private val binding: FragmentUserBinding by viewBinding()
    private lateinit var userRepo: UserRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        getAllUser()
    }

    private fun initData() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        userRepo = UserRepository(requireContext())
        setHasOptionsMenu(true)
    }

    private fun getAllUser() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            binding.progressBar.visible(true)
            try {
                val usersList = userRepo.getAllUsers()

                showDataInRecyclerView(usersList)
            } catch (e: Exception) {
                requireView().snackBar(e.stackTraceToString())
                Log.e(TAG, "getAllUser: ${e.printStackTrace()}")
                binding.progressBar.visible(false)
            }

        }
    }

    private fun showDataInRecyclerView(usersList: ArrayList<User>) {
        val mAdapter = UsersAdapter(usersList, this@UsersFragment)
        binding.recyclerView.adapter = mAdapter
        binding.progressBar.visible(false)
    }

    override fun clickListener(user: User) {

        if (findNavController().currentDestination?.id == R.id.navigation_user) {
            UserFragmentDirections.actionNavigationUserToUserDetailsFragment(user).apply {
                findNavController().navigate(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.searchQuery { query ->
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    binding.recyclerView.scrollToPosition(0)
                    val searchUsers = userRepo.searchAllUsers(query)

                    showDataInRecyclerView(searchUsers)
                } catch (e: Exception) {
                    requireView().snackBar(e.message.toString())
                }
            }

        }

    }


}

