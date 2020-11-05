package com.mjb.ytmp.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.api.services.youtube.model.PlaylistItem
import com.mustahsan.androidkit.date.shortTime24
import java.time.Duration

class PlayListItemX(
    var item: PlaylistItem,
    var playtime: String? = null,
) {
    val author: String
        get() = item.snippet.channelTitle ?: ""

    val durationFormatted: String
        get() {
            //PT4M15S
            if (playtime.isNullOrEmpty()) return ""
            var d = playtime!!.replace("PT", "")
            var time = ""
            if (d.contains("H")) {
                val split = d.split("H")
                d = split.last()
                time += "${split.first()}:"
            }
            if (d.contains("M")) {
                val split = d.split("M")
                d = split.last()
                time += "${split.first()}:"
            }
            if (d.contains("S")) {
                val split = d.split("S")
                time += "${split.first()}"
            }
            return time
        }
}