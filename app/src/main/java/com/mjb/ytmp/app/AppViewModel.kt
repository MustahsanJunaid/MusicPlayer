package com.mjb.ytmp.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class AppViewModel<YTModel> : ViewModel(){
    val waiting: MutableLiveData<Boolean> = MutableLiveData()
    val liveList: MutableLiveData<List<YTModel>> = MutableLiveData()
    protected val list = mutableListOf<YTModel>()
}