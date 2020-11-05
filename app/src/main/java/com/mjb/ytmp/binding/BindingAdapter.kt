package com.mjb.ytmp.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.api.services.youtube.model.Thumbnail
import com.google.api.services.youtube.model.ThumbnailDetails
import com.mjb.ytmp.ktx.setImageUrl
import com.mjb.ytmp.ktx.thumbUrl

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, imageUrl: String?) {
        imageUrl?.let {
            imageView.setImageUrl(it)
        }
    }

    @JvmStatic
    @BindingAdapter("thumbnail")
    fun setThumbnail(imageView: ImageView, thumbnail: ThumbnailDetails?) {
        thumbnail?.let { thumb ->
            thumb.thumbUrl?.let { imageView.setImageUrl(it) }
        }
    }
}