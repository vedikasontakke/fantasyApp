package com.example.fantansyapp.ui.transactions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fantansyapp.data.models.Transaction
import com.example.fantansyapp.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat

class TransactionAdapter(private val transactionDataList: ArrayList<Transaction>, private val clickListener:OnTranactionClickListener) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            //if user click row
            binding.root.setOnClickListener {

                if (adapterPosition != RecyclerView.NO_POSITION){
                    clickListener.clickListener(transactionDataList[adapterPosition])
                }
            }

        }

        @SuppressLint("SimpleDateFormat")
        fun bindData(transaction: Transaction) {
            binding.apply {

                email.text = transaction.email
                coins.text = transaction.coins.toString()
                plan.text = transaction.plan

                try {
                    val singleDate = transaction.date.split("-")
                    val date = SimpleDateFormat("dd-MM-yyyy").parse(transaction.date)
                    year.text = singleDate[2]
                    this.date.text = singleDate[0]
                    month.text = SimpleDateFormat("MMM").format(date)
                } catch (e:Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindData(transactionDataList[position])
    }

    override fun getItemCount() = transactionDataList.size

    interface OnTranactionClickListener {
        fun clickListener(transaction: Transaction)
    }
}