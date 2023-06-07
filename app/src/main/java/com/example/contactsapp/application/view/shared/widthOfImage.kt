package com.example.contactsapp.application.view.shared

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

private const val roundingRadiusImage = 100

fun AppCompatImageView.setImageOfContact(
    imageUrl: String,
    widthOfImage: Int = 200,
    heightOfImage: Int = 200
) {
    Glide.with(this.context)
        .load(imageUrl)
        .override(widthOfImage, heightOfImage)
        .transform(RoundedCorners(roundingRadiusImage))
        .into(this)
}