package com.mjb.ytmp.ui.content

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.api.services.youtube.model.Playlist
import com.google.api.services.youtube.model.PlaylistItem
import com.mjb.ytmp.databinding.ItemPlayListBinding
import com.mjb.ytmp.databinding.ItemPlayListContentBinding
import com.mjb.ytmp.model.PlayListItemX
import com.mustahsan.androidkit.recyclerview.DataBoundRecyclerAdapter

class ContentDetailsAdapter(private val itemClicked: (PlayListItemX) -> Unit) :
    DataBoundRecyclerAdapter<PlayListItemX, ItemPlayListContentBinding>() {
    override fun bind(binding: ItemPlayListContentBinding, item: PlayListItemX, position: Int) {
        binding.playlist = item
        binding.root.setOnClickListener { itemClicked(item) }
    }

    override fun createBinding(parent: ViewGroup): ItemPlayListContentBinding {
        return ItemPlayListContentBinding.inflate(LayoutInflater.from(parent.context))
    }
}