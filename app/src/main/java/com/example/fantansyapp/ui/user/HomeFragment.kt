package com.example.fantansyapp.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fantansyapp.R
import com.example.fantansyapp.data.adapter.UserAdapter
import com.example.fantansyapp.data.reoositiries.UserReposeitory
import com.example.fantansyapp.databinding.FragmentUserBinding
import com.example.fantansyapp.utils.NoInternetException
import com.example.fantansyapp.utils.snackBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.item_layout.view.*
import org.json.JSONException
import java.net.HttpURLConnection

private const val TAG = "HomeFragment"
class UserFragment : Fragment(R.layout.fragment_user) {

    private val binding: FragmentUserBinding by viewBinding()
    private lateinit var userRepo:UserReposeitory

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<UserAdapter.ViewHolder>? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_layout, container, false)
    }

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
               // val countriesList: ArrayList<Countries>? = response.body()?.Countries

              /*  for(user in usersList){

                    Log.e(TAG, "getAllUser: ${user.name}" )
                }*/

                recycler_view_main.apply {
                    // set a LinearLayoutManager to handle Android
                    // RecyclerView behavior
                    layoutManager = LinearLayoutManager(activity)
                    // set the custom adapter to the RecyclerView
                    adapter = UserAdapter(usersList)

                    for(user in usersList){

                       // Log.e(TAG, "getAllUser: ${user.name}" )

                     /*   user_name.text = user.name
                        user_email.text = user.email
                        user_plan.text = user.plan
                        user_coins.text = user.coins.toString()
                        user_profile.
                        */

                        val countryAdapter = UserAdapter(usersList)
                        recycler_view_main.adapter = countryAdapter


                    }

                }

            }catch (e: Exception){
                requireView().snackBar(e.toString())
            }
            catch (e: NoInternetException) {
                requireView().snackBar(e.toString())
            } catch (e: JSONException) {
                requireView().snackBar(e.toString())
            } catch (e: java.lang.Exception) {
                requireView().snackBar(e.toString())
            }

        }
    }
}

