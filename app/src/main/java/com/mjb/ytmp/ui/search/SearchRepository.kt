package com.mjb.ytmp.ui.search

import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import com.mjb.ytmp.util.YoutubeHelper

class SearchRepository() {

    fun search(youTube: YouTube, query: String, pageToken: String?): SearchListResponse? {
        val request = youTube.search().list("id,contentDetails,snippet")
            .setPageToken(pageToken)
            .setMaxResults(YoutubeHelper.MAX)
            .setFields("items(contentDetails/videoId,snippet),nextPageToken,pageInfo")
            .setQ(query)
        return request.execute()
    }

    companion object {
        val instance: SearchRepository by lazy {
            SearchRepository()
        }
    }
}