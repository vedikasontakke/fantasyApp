package com.example.fantansyapp.data.models

import com.example.fantansyapp.utils.Constants.Companion.SLIDER_URL

data class Slider(

        val id: String,
        val image: String
){
    val getImage get() = SLIDER_URL + image
}