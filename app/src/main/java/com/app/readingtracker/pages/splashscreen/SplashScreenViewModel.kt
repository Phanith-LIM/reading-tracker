package com.app.readingtracker.pages.splashscreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.readingtracker.core.DataStoreManager
import kotlinx.coroutines.flow.firstOrNull

class SplashScreenViewModel : ViewModel() {
    suspend fun checkToken(context: Context): Boolean {
        val token = DataStoreManager.read(context, "token").firstOrNull()
        Log.d("Token", token.toString())
        return !token.isNullOrEmpty()
    }
}