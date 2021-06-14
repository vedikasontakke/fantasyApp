package com.example.fantansyapp.ui.history

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.History
import com.example.fantansyapp.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat

class HistoryAdapter(private val histroyDataList: ArrayList<History>) : RecyclerView.Adapter<HistoryAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SimpleDateFormat", "ResourceAsColor")
        fun bindData(history: History) {
            binding.apply {

               reason.text = history.reason

                if (history.type == "add"){
                    val newCoins = "+ ${history.coins}"
                    binding.coins.text = newCoins
                    binding.coins.setTextColor(Color.parseColor("#17AE17"))
                }else{
                    val newCoins = "- ${history.coins}"
                    binding.coins.text = newCoins
                    binding.coins.setTextColor(Color.parseColor("#F44336"))
                }

                try {
                    val singleDate = history.date.split("-")

                    year.text = singleDate[0]
                    this.date.text = singleDate[2]
                    month.text = singleDate[1]
                } catch (e:Exception) {
                    e.printStackTrace()
                }


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindData(histroyDataList[position])
    }

    override fun getItemCount() = histroyDataList.size


}