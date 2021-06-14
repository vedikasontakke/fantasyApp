package com.example.fantansyapp.ui.userDetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.data.repositories.UserRepository
import com.example.fantansyapp.databinding.FragmentUserDetailsBinding
import com.example.fantansyapp.utils.snackBar
import com.example.fantansyapp.utils.visible
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

                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Update ${user.name}")
                    setMessage("Do your really want to Update user")
                    setPositiveButton("Yes") { dialog, _ ->
                        updateDetails()
                        dialog.cancel()
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    create()
                    show()
                }



            }

        }

        (requireContext() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

    }

    private fun showDelteAlertDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete ${user.name}")
            setMessage("Do your really want to delete ${user.email}")
            setPositiveButton("Yes") { dialog, _ ->
                deleteUser(user.email)
                dialog.cancel()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            create()
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.user_operation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.action_delete){
            showDelteAlertDialog()
        }else if(item.itemId == R.id.action_show_history){
            navigateToHistory()
        }else{

        }
        return true


    }

    private fun updateDetails() {

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



        viewLifecycleOwner.lifecycleScope.launch {

            binding.progressBarUserdetails.visible(true)

            try {

                val result = UserRepository(requireContext()).updateSingleUser(dataToUpdate)
                if (result.result == "success") {
                    binding.root.snackBar("User Data Updated Successfully")
                }

                binding.progressBarUserdetails.visible(false)

            } catch (e: Exception) {
                binding.root.snackBar(e.localizedMessage.toString())
                binding.progressBarUserdetails.visible(false)

            }
        }
    }

    private fun deleteUser(email: String) {
        viewLifecycleOwner.lifecycleScope.launch {

            binding.progressBarUserdetails.visible(true)

            try {

                val result = UserRepository(requireContext()).deleteUser(email)
                if (result.result == "User Deleted Successfully") {
                    binding.root.snackBar("User Deleted Successfully")

                    binding.progressBarUserdetails.visible(false)


                    findNavController().popBackStack()
                }
            } catch (e: Exception) {
                binding.root.snackBar(e.localizedMessage.toString())

                binding.progressBarUserdetails.visible(false)

            }
        }
    }

    private fun navigateToHistory(){
        try {
            UserDetailsFragmentDirections.actionUserDetailsFragmentToHistoryFragment(user.email).apply {
                findNavController().navigate(this)
            }
        } catch (e: Exception) {

            try {
                if (e is UninitializedPropertyAccessException){
                    binding.root.snackBar("Pleas Wait fetching data")
                }else{
                    binding.root.snackBar(e.message.toString())
                }
            }catch (e:Exception){

            }
        }
    }


}