package com.mjb.ytmp.ui.playlist

import androidx.lifecycle.viewModelScope
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Playlist
import com.mjb.ytmp.app.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val repo: PlaylistRepository = PlaylistRepository.instance
) : AppViewModel<Playlist>() {

    fun getMyPlayList(youTube: YouTube, page: String? = null) {
        viewModelScope.launch(Dispatchers.Default) {
            waiting.postValue(true)
            getPlayList(youTube, page)
            waiting.postValue(false)
        }
    }

    private fun getPlayList(youTube: YouTube, page: String? = null) {
        if (page.isNullOrEmpty()) {
            list.clear()
        }
        repo.getPlayList(youTube, page)?.let { response ->
            val items = response.items
            if (!items.isNullOrEmpty()) {
                list.addAll(items)
            }
            if (!response.nextPageToken.isNullOrEmpty()) {
                getPlayList(youTube, response.nextPageToken)
            }
        }
        liveList.postValue(list)
    }
}