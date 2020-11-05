package com.mjb.ytmp.ui.content

import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistItemListResponse
import com.google.api.services.youtube.model.VideoListResponse

class ContentDetailsRepository() {

    fun getPlayListDetails(
        youTube: YouTube,
        playlistId: String,
        pageToken: String? = null
    ): PlaylistItemListResponse {
        val request = youTube.playlistItems().list("id,contentDetails,snippet")
            .setPlaylistId(playlistId)
            .setFields("items(contentDetails/videoId,snippet),nextPageToken,pageInfo")
        // Only retrieve data used in this application, thereby making
        // the application more efficient. See:
        // https://developers.google.com/youtube/v3/getting-started#partial


        if (!pageToken.isNullOrEmpty()) {
            request.pageToken = pageToken
        }

        return request.execute()
    }

    fun getVideoDetail(youTube: YouTube, videoId: String): VideoListResponse? {
        val videoRequest = youTube.videos().list("id,contentDetails")
        videoRequest.id = videoId
        return videoRequest.execute()
    }

    companion object {
        val instance: ContentDetailsRepository by lazy {
            ContentDetailsRepository()
        }
    }
}