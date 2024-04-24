package com.app.readingtracker.pages.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var counter = MutableLiveData<Int>(0)

    fun onIncrease() {
        counter.value = counter.value?.plus(1)
    }

    fun onDecrease() {
        counter.value = counter.value?.minus(1)
    }

    init {
        Log.i("HomeViewModel", "init")
    }
}