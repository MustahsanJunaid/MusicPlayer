package com.mjb.ytmp.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.mjb.ytmp.app.AppViewModel
import com.mjb.ytmp.app.CompactActivity
import com.mjb.ytmp.databinding.ActivityMainBinding
import com.mjb.ytmp.ui.playlist.PlayListActivity
import com.mjb.ytmp.util.YoutubeHelper
import com.mustahsan.androidkit.alert.snackBarShort
import com.mustahsan.androidkit.log.logW

class AuthActivity : CompactActivity<AppViewModel<*>>() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!checkAlreadyLogin()) {
            initAuth()
        }
    }

    private fun checkAlreadyLogin(): Boolean {
        return if (googleSignInAccount != null && googleSignInAccount?.isExpired == false) {
            PlayListActivity.start(this)
            finish()
            true
        } else {
            false
        }
    }

    private fun initAuth() {

        binding.authButton.setOnClickListener {
            val signInIntent: Intent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_AUTH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_AUTH) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            googleSignInAccount = completedTask.getResult(ApiException::class.java)
            googleSignInAccount?.let {
                PlayListActivity.start(this)
                finish()
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            logW("signInResult:failed code=" + e.statusCode)
            binding.root.snackBarShort("Sign in failed: ${e.statusCode}")
        }
    }


    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, AuthActivity::class.java))
            activity.finish()
        }

        const val RC_AUTH = 1234
    }
}