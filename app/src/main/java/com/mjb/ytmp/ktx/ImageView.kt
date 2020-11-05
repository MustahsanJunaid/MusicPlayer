package com.mjb.ytmp.ktx

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.MediaStoreSignature

fun ImageView.setImageUrl(
    path: String,
    modified: Long = 0
) {
    Glide.with(this)
        .load(path)
        .signature(MediaStoreSignature("", modified, 1))
        .centerCrop()
//        .placeholder(placeholder)
        .into(this)
}