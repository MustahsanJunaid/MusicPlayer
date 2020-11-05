package com.mjb.ytmp.ui.playlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.mjb.ytmp.R
import com.mjb.ytmp.app.CompactActivity
import com.mjb.ytmp.databinding.ActivityHomeBinding
import com.mjb.ytmp.ui.auth.AuthActivity
import com.mjb.ytmp.ui.content.ContentDetailsActivity
import com.mjb.ytmp.util.YoutubeHelper
import com.mustahsan.androidkit.recyclerview.VerticalSpacingDecoration

class PlayListActivity : CompactActivity<PlaylistViewModel>(
    PlaylistViewModel::class.java
) {
    lateinit var binding: ActivityHomeBinding
    private val adapter = PlayListRecyclerAdapter {
        ContentDetailsActivity.start(this, it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        binding.contentPanel.recyclerView.adapter = adapter
        binding.contentPanel.recyclerView.addItemDecoration(
            VerticalSpacingDecoration(
                resources.getInteger(R.integer.span),
                resources.getDimension(R.dimen.item_spacing).toInt(),
                true
            )
        )
        viewModel?.liveList?.observe(this, {
            adapter.data = it.toMutableList()
        })
        viewModel?.waiting?.observe(this, { wait ->
            if (wait) {
                showLoading(getString(R.string.loading_data))
            } else {
                hideLoading()
            }
        })
        val youtube = YoutubeHelper.getYoutubeInstance(this, googleSignInAccount!!)
        viewModel?.getMyPlayList(youtube)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout -> {
                googleSignInClient.signOut()
                AuthActivity.start(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun start(context: Context) = with(context) {
            val intent = Intent(this, PlayListActivity::class.java)
            startActivity(intent)
        }
    }
}