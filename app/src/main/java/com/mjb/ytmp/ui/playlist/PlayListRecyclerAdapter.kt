package com.mjb.ytmp.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.api.services.youtube.model.Playlist
import com.mjb.ytmp.databinding.ItemPlayListBinding
import com.mustahsan.androidkit.recyclerview.DataBoundRecyclerAdapter

class PlayListRecyclerAdapter(private val itemClicked: (Playlist) -> Unit) :
    DataBoundRecyclerAdapter<Playlist, ItemPlayListBinding>() {
    override fun bind(binding: ItemPlayListBinding, item: Playlist, position: Int) {
        binding.playlist = item
        binding.root.setOnClickListener { itemClicked(item) }
    }

    override fun createBinding(parent: ViewGroup): ItemPlayListBinding {
        return ItemPlayListBinding.inflate(LayoutInflater.from(parent.context))
    }
}