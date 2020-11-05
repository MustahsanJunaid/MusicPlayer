package com.mjb.ytmp.ui.playlist

import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistListResponse
import com.mjb.ytmp.util.YoutubeHelper

class PlaylistRepository() {

    fun getPlayList(youTube: YouTube, pageToken: String? = null): PlaylistListResponse? {
        return youTube.playlists().list("contentDetails,snippet")
            .setMine(true)
            .setMaxResults(YoutubeHelper.MAX)
            .setPageToken(pageToken)
            .execute()
    }

    companion object {
        val instance: PlaylistRepository by lazy {
            PlaylistRepository()
        }
    }
}