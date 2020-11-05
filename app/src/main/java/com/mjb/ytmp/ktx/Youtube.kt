package com.mjb.ytmp.ktx

import com.google.api.services.youtube.model.Playlist
import com.google.api.services.youtube.model.ThumbnailDetails

val ThumbnailDetails.thumbUrl: String?
    get() = when {
        standard != null -> standard.url
        medium != null -> medium.url
        high != null -> high.url
        else -> null
    }