package com.mjb.ytmp.ui.content

import androidx.lifecycle.viewModelScope
import com.google.api.services.youtube.YouTube
import com.mjb.ytmp.app.AppViewModel
import com.mjb.ytmp.model.PlayListItemX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContentDetailsViewModel(
    private val repo: ContentDetailsRepository = ContentDetailsRepository.instance
) : AppViewModel<PlayListItemX>() {

    private val indexIdMap = mutableMapOf<String, Int>()

    fun getPlayListDetails(
        youTube: YouTube,
        playlistId: String,
        pageToken: String? = null
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            waiting.postValue(true)
            getDetails(youTube, playlistId, pageToken)
        }
    }

    private fun getDetails(
        youTube: YouTube,
        playlistId: String,
        pageToken: String? = null
    ) {
        if (pageToken.isNullOrEmpty()) {
            list.clear()
            indexIdMap.clear()
        }
        repo.getPlayListDetails(youTube, playlistId, pageToken).let { response ->
            val items = response.items
            if (!items.isNullOrEmpty()) {
                val xItems = mutableListOf<PlayListItemX>()
                for (i in items.indices) {
                    val item = items[i]
                    indexIdMap[item.contentDetails.videoId] = i
                    xItems.add(PlayListItemX(item))
                }
                list.addAll(xItems)
            }
            if (!response.nextPageToken.isNullOrEmpty()) {
                getPlayListDetails(youTube, playlistId, response.nextPageToken)
            }
        }
        liveList.postValue(list)
        waiting.postValue(false)
        getVideoDetail(youTube, list)
    }

    private fun getVideoDetail(youTube: YouTube, xList: List<PlayListItemX>) {
        for (item in xList) {
            val itemId = item.item.contentDetails.videoId
            val response = repo.getVideoDetail(youTube, itemId)
            if (response != null) {
                with(response.items) {
                    if (!isNullOrEmpty()) {
                        first().contentDetails?.let { content ->
                            val index = indexIdMap[itemId] ?: -1
                            if (index >= 0 && index < list.size) {
                                list[index].playtime = content.duration
                            }
                        }
                    }
                }
            }
        }
        liveList.postValue(list)
    }
}