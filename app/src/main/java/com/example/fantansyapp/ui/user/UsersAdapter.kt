package com.example.fantansyapp.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fantansyapp.data.models.User
import com.example.fantansyapp.databinding.ItemUsersBinding


import com.example.fantansyapp.utils.setGlideImage

class UsersAdapter(private var userDataList: ArrayList<User>, private val clickListener:OnUserClickListener) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    inner class UsersViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            //if user click row
            binding.root.setOnClickListener {

                if (adapterPosition != RecyclerView.NO_POSITION){
                    clickListener.clickListener(userDataList[adapterPosition])
                }
            }

        }

        fun bindData(user: User) {
            binding.apply {
                name.text = user.name
                email.text = user.email
                coins.text = user.coins.toString()
                plan.text = user.plan
                profileImage.setGlideImage(binding.root.context,user.getImage)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }


    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindData(userDataList[position])
    }

    override fun getItemCount() = userDataList.size

    interface OnUserClickListener {
        fun clickListener(user: User)
    }

    fun search(searchUsers:ArrayList<User>){
        this.userDataList = searchUsers
        notifyDataSetChanged()
    }
}