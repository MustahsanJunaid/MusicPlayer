package com.mjb.ytmp.ui.auth

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
import com.mjb.ytmp.app.CompactActivity
import com.mjb.ytmp.databinding.ActivityMainBinding
import com.mjb.ytmp.ui.home.HomeActivity
import com.mjb.ytmp.util.YoutubeHelper
import com.mustahsan.androidkit.alert.snackBarShort
import com.mustahsan.androidkit.log.logW

class AuthActivity : CompactActivity<ViewModel>() {
    lateinit var binding: ActivityMainBinding

    var googleSignInAccount: GoogleSignInAccount? = null
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!checkAlreadyLogin()) {
            initAuth()
        }
    }

    private fun checkAlreadyLogin(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        return if (account != null && !account.isExpired) {
            HomeActivity.start(this)
            finish()
            true
        } else {
            false
        }
    }

    private fun initAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(YoutubeHelper.SCOPE))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
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
                HomeActivity.start(this)
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
        const val RC_AUTH = 1234
    }
}