package com.example.mynewsapikt.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

inline fun ImageView.glideLoadImage(context: Context,uri: Uri)
{
    Glide.with(context).load(uri).into(this)
}