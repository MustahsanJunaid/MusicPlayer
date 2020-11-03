package com.mjb.ytmp.ktx

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.mjb.ytmp.R
import com.mjb.ytmp.util.DialogBuilder

fun Context.waitingDialog(cancelable: Boolean = false, builderFunction: Dialog.() -> Any): Dialog {
    val dialog = DialogBuilder.with(activity)
        .contentView(R.layout.dialog_waiting)
        .cancelable(cancelable)
        .transparent(true)
        .build()
    dialog.show()
    dialog.builderFunction()
    return dialog
}

//Part of Functions.kt, this and below 3 functions helps to create dialog.
fun Context.alertDialog(cancelable: Boolean = false, builderFunction: Dialog.() -> Any) {

    val dialog = DialogBuilder.with(this)
        .contentView(R.layout.dialog_alert)
        .cancelable(cancelable)
        .transparent(true)
        .build()
    dialog.builderFunction()
    dialog.show()
}


fun Dialog.title(title: String = "Alert") {
    this.findViewById<TextView>(R.id.titleTextView)?.text = title
}

fun Dialog.message(message: String = "Alert") {
    this.findViewById<TextView>(R.id.messageTextView)?.text = message
}

fun Dialog.positiveButton(text: String = context.getString(R.string.action_okay), handleClick: () -> Unit = {}) {
    val positiveButton = this.findViewById<AppCompatButton>(R.id.positiveButton)
    positiveButton.text = text
    positiveButton.visibility = View.VISIBLE
    positiveButton.setOnClickListener {
        dismiss()
        handleClick()
    }
}

fun Dialog.negativeButton(text: String = context.getString(R.string.action_cancel), handleClick: () -> Unit = {}) {
    val negativeButton = this.findViewById<AppCompatButton>(R.id.negativeButton)
    negativeButton.text = text
    negativeButton.visibility = View.VISIBLE
    negativeButton.setOnClickListener {
        dismiss()
        handleClick()
    }
}

//fun Dialog.neutralButton(text: String, handleClick: () -> Unit = {}) {
//    val neutralButton = this.findViewById<AppCompatButton>(R.id.neutralButton)
//    neutralButton.text = text
//    neutralButton.visibility = View.VISIBLE
//    neutralButton.setOnClickListener {
//        dismiss()
//        handleClick()
//    }
//}
