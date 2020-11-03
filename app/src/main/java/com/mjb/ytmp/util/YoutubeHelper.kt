package com.mjb.ytmp.util

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTubeScopes

object YoutubeHelper {
    const val BASE_URL: String = "https://www.googleapis.com/youtube/v3/"
    const val SEARCH = "search"

    const val SCOPE = YouTubeScopes.YOUTUBE_READONLY

    fun getYoutubeInstance(context: Context, googleSignInAccount: GoogleSignInAccount): YouTube {
        val credential = GoogleAccountCredential.usingOAuth2(
            context, setOf(SCOPE)
        )
        credential.selectedAccount = googleSignInAccount.account
        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()

        return YouTube.Builder(transport, jsonFactory, credential)
            .setApplicationName("Music Player")
            .build()
    }
}