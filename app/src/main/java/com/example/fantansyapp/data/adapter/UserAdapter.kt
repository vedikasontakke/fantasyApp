package com.example.fantansyapp.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fantansyapp.data.models.User

class UserAdapter(val Userlist: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: UserAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        return ViewHolder(
            UserAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.binding.tvExample.text = Userlist[position].text

    }

    override fun getItemCount(): Int {
        return Userlist.size
    }

}