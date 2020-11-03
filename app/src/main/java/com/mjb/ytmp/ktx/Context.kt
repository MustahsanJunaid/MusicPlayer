package com.mjb.ytmp.ktx

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

val Context.activity: Activity?
    get() {
        var mContext = this
        while (mContext is ContextWrapper) {
            if (mContext is Activity) {
                return mContext
            }
            mContext = mContext.baseContext
        }
        return null
    }