package com.mjb.ytmp.app

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.mjb.ytmp.R
import com.mjb.ytmp.ktx.*
import com.mjb.ytmp.util.YoutubeHelper

abstract class CompactActivity<V : AppViewModel<*>>(
    private val vmClass: Class<V>? = null
) : AppCompatActivity() {

    var googleSignInAccount: GoogleSignInAccount? = null
    val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(YoutubeHelper.SCOPE))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(this, gso)
    }

    protected var viewModel: V? = null

    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize viewModel
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        vmClass?.let {
            viewModel = ViewModelProvider(this).get(it)
        }
    }


    fun showLoading(message: String) {
        if (progressDialog != null && progressDialog!!.isShowing) progressDialog!!.dismiss()
        progressDialog = waitingDialog {
            setCancelable(false)
            title(getString(R.string.msg_please_wait))
            message(message)
        }
    }

    fun hideLoading() {
        progressDialog?.dismiss()
    }

    fun alertError(message: String) {
        alertDialog(true) {
            title(getString(R.string.title_error))
            message(message)
            positiveButton()
        }
    }

}