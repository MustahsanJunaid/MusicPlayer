package com.mjb.ytmp.model

import android.os.Parcelable
import com.google.api.services.youtube.model.Playlist
import com.mjb.ytmp.ktx.thumbUrl
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayListParcel(
    val title: String,
    val playlistId: String,
    val channelId: String,
    val channelTitle: String,
    val videos: Long,
    val thumbnail: String?
) : Parcelable {
    companion object {
        fun create(playlist: Playlist): PlayListParcel {
            return PlayListParcel(
                playlist.snippet.title,
                playlist.id,
                playlist.snippet.channelId,
                playlist.snippet.channelTitle,
                playlist.contentDetails.itemCount ?: 0,
                playlist.snippet.thumbnails.thumbUrl
            )
        }
    }
}