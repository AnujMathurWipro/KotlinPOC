package com.anuj.pockotlin

import android.widget.ImageView

import androidx.databinding.BindingAdapter

import com.anuj.pockotlin.log.Logger
import com.anuj.pockotlin.util.Utility
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Logger.debugLog("URL to be loaded = $url")

    if (Utility.isValidURL(url))
        Picasso.get().load(url).placeholder(R.drawable.ic_image_black_24dp).error(R.drawable.ic_image_black_24dp).into(view)
    else
        view.setImageResource(R.drawable.ic_image_black_24dp)
}

