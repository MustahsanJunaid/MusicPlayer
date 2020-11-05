package com.mjb.ytmp.ui.search

import androidx.lifecycle.viewModelScope
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchResult
import com.mjb.ytmp.app.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repo: SearchRepository = SearchRepository.instance
) : AppViewModel<SearchResult>() {

    fun search(youTube: YouTube, query: String, pageToken: String? = null) {
        if (pageToken.isNullOrEmpty()) {
            list.clear()
        }
        viewModelScope.launch(Dispatchers.Default) {
            val response = repo.search(youTube, query, pageToken)
            if (response?.items.isNullOrEmpty()) {

                list.addAll(response!!.items)

                if (!response.nextPageToken.isNullOrEmpty()) {
                    search(youTube, query, response?.nextPageToken)
                }
            }
            liveList.postValue(list)
        }
    }
}