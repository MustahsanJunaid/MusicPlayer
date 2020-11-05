package com.mjb.ytmp.ui.content

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Video.Thumbnails.VIDEO_ID
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.google.api.services.youtube.model.Playlist
import com.mjb.ytmp.R
import com.mjb.ytmp.app.CompactActivity
import com.mjb.ytmp.databinding.ActivityContentDetailsBinding
import com.mjb.ytmp.model.PlayListParcel
import com.mjb.ytmp.util.YoutubeHelper
import com.mustahsan.androidkit.recyclerview.VerticalSpacingDecoration
import kotlinx.android.synthetic.main.activity_home.*


class ContentDetailsActivity : CompactActivity<ContentDetailsViewModel>(
    ContentDetailsViewModel::class.java
) {
    lateinit var binding: ActivityContentDetailsBinding
    private val adapter = ContentDetailsAdapter {
        val videoId = it.item.contentDetails.videoId
        val intent = YouTubeStandalonePlayer.createVideoIntent(
            this,
            getString(R.string.google_client_secret),
            videoId
        )
        startActivity(intent)
    }

    private var parcel: PlayListParcel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        val youtube = YoutubeHelper.getYoutubeInstance(this, googleSignInAccount!!)

        with(intent) {
            parcel = getParcelableExtra(PARCEL)
        }
        binding.playlistParcel = parcel
        parcel?.let {
            title = it.title
        }

        binding.contentPanel.recyclerView.adapter = adapter
        binding.contentPanel.recyclerView.addItemDecoration(
            VerticalSpacingDecoration(
                resources.getInteger(R.integer.span),
                resources.getDimension(R.dimen.item_spacing).toInt(),
                true
            )
        )
        viewModel?.waiting?.observe(this, { wait ->
            if (wait) {
                showLoading(getString(R.string.loading_data))
            } else {
                hideLoading()
            }
        })
        viewModel?.liveList?.observe(this, {
            adapter.data = it.toMutableList()
        })

        viewModel?.getPlayListDetails(youtube, parcel!!.playlistId)

    }

    companion object {
        private const val PARCEL = "PARCEL"
        fun start(context: Context, playlist: Playlist) = with(context) {
            val intent = Intent(this, ContentDetailsActivity::class.java).apply {
                putExtra(PARCEL, PlayListParcel.create(playlist))
            }
            startActivity(intent)
        }
    }
}