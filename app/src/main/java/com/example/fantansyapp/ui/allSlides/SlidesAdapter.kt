package com.example.fantansyapp.ui.allSlides

import android.annotation.SuppressLint
import android.transition.Slide
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fantansyapp.data.models.Slider
import com.example.fantansyapp.data.models.Transaction
import com.example.fantansyapp.databinding.ItemSlideBinding
import com.example.fantansyapp.utils.setGlideImage

class SlidesAdapter(private val slidesList: ArrayList<Slider>, private val clickListener:OnDeleteClickListener) : RecyclerView.Adapter<SlidesAdapter.SlidesViewModel>() {

    inner class SlidesViewModel(val binding: ItemSlideBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            //if user click row
            binding.delete.setOnClickListener {

                if (adapterPosition != RecyclerView.NO_POSITION){
                    clickListener.clickListener(slidesList[adapterPosition].id)
                }
            }

        }

        @SuppressLint("SimpleDateFormat")
        fun bindData(slide: Slider) {




            binding.apply {

                this.slide.setGlideImage(binding.root.context,slide.getImage)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlidesViewModel {
        val binding = ItemSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlidesViewModel(binding)
    }


    override fun onBindViewHolder(model: SlidesViewModel, position: Int) {
        model.bindData(slidesList[position])
    }

    override fun getItemCount() = slidesList.size

    interface OnDeleteClickListener {
        fun clickListener(slideId: String)
    }
}