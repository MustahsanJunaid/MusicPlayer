package com.mjb.ytmp.app

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mjb.ytmp.R
import com.mjb.ytmp.ktx.*

abstract class CompactActivity<V : ViewModel>(
    private val vmClass: Class<V>? = null
) : AppCompatActivity() {

    protected var viewModel: V? = null

    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize viewModel
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