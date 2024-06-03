package com.app.readingtracker.pages.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.readingtracker.core.BaseRepository
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.book.BookModelCount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ProfileViewModel : ViewModel() {
    private val baseRepository: BaseRepository = BaseRepository()

    private val _uiState = MutableStateFlow<UiState>(UiState.LOADING)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _profileData = MutableStateFlow<ProfileModel?>(null)
    val profileData: StateFlow<ProfileModel?> = _profileData.asStateFlow()


    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    suspend fun getProfile(token: String) {
        Log.d("MyToken", token)
        baseRepository.setHeader(token)
        viewModelScope.launch {
            _uiState.value = UiState.LOADING
            try {
                val response = baseRepository.get("users/meta")
                if(response != "") {
                    _profileData.value = Json.decodeFromString(response)
                    _uiState.value = UiState.SUCCESS
                }
            } catch (e: Exception) {
                _uiState.value = UiState.ERROR
                _errorMessage.value = e.message ?: ""
                Log.d("Error API", e.message ?: "")
            }
        }
    }
}