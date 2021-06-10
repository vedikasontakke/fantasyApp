package com.example.fantansyapp.data.adapter

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.User
import com.example.mynewsapikt.utils.OnRecyclerViewItemClickListener
import com.example.mynewsapikt.utils.glideLoadImage
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonDisposableHandle.parent

class UserAdapter(val Userlist: List<User> , val listener: OnRecyclerViewItemClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: UserAdapterBinding) : RecyclerView.ViewHolder(binding.root){

        val txtUserProfile: ImageView = binding.root.findViewById(R.id.user_profile)
        val txtUserName: TextView = binding.root.findViewById(R.id.user_name)
        val txtUseremail: TextView = binding.root.findViewById(R.id.user_email)
        val txtUserCoins: TextView = binding.root.findViewById(R.id.user_coins)
        val txtUserPlan: TextView = binding.root.findViewById(R.id.user_coins)


        val UserAdapterParentLinear: RelativeLayout = binding.root.findViewById(R.id.article_adapter_ll_parent)

        init {
            UserAdapterParentLinear.setOnClickListener {

                listener.onItemClick(Userlist[adapterPosition].profileImage)

            }
        }

        fun bindData(user:User){
            user.apply {

                txtUserName.text = name
                txtUseremail.text = email
                txtUserCoins.text = coins.toString()
                 txtUserPlan.text = plan
                txtUserProfile.glideLoadImage(itemView.context,profileImage!!.toUri())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        return ViewHolder(
            UserAdapterBinding.inflate(
                LayoutInflater.from(parent.context),  /// item_layout
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {

        holder.bindData(Userlist[position])


    }

    override fun getItemCount(): Int {
        return Userlist.size
    }

}
