package com.app.readingtracker.pages.sign_in

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.app.readingtracker.core.BaseRepository
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.PageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class SignInViewModel : ViewModel() {
    private val _userState = MutableStateFlow<FirebaseUser?>(null)
    val userState: StateFlow<FirebaseUser?> = _userState
    private val baseRepository: BaseRepository = BaseRepository()

    private val _uiState = MutableStateFlow<UiState?>(null) // Fixed here
    val uiState: StateFlow<UiState?> = _uiState.asStateFlow()

    init {
        observeUserChanges()
    }

    fun setUser(user: FirebaseUser? = null) {
        _userState.value = user
    }

    fun getToken(context: Context, userId: String, navigator: Navigator? = null) {
        viewModelScope.launch {
            _uiState.value = UiState.LOADING
            try {
                val body = mapOf("userId" to userId)
                val jsonBody = Json.encodeToString(body)
                val response = baseRepository.post("users/verify", jsonBody)
                if (response.isNotEmpty()) {
                    _uiState.value = UiState.SUCCESS
                    val data: TokenModel = Json.decodeFromString(response)
                    DataStoreManager.write(context, "token", data.accessToken)
                    DataStoreManager.write(context, "refresh", data.refreshToken)
                    navigator?.replaceAll(PageView())
                }
            } catch (e: Exception) {
                _uiState.value = UiState.ERROR
                Log.d("Error API", e.message ?: "")
            }
        }
    }

    private fun observeUserChanges() {
        FirebaseAuth.getInstance().addAuthStateListener { firebaseAuth ->
            viewModelScope.launch {
                _userState.value = firebaseAuth.currentUser
            }
        }
    }
}
