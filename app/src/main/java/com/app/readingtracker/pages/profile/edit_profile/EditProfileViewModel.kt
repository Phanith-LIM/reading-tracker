package com.app.readingtracker.pages.profile.edit_profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.readingtracker.core.BaseRepository
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.profile.ProfileModel
import com.app.readingtracker.share.helper.convertUriToFile
import com.app.readingtracker.share.sealed.ImageData
import com.github.kittinunf.fuel.core.FileDataPart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class EditProfileViewModel : ViewModel() {
    private val baseRepository: BaseRepository = BaseRepository()

    private val _uiState = MutableStateFlow(UiState.LOADING)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _profileData = MutableStateFlow<ProfileModel?>(null)
    val profileData: StateFlow<ProfileModel?> = _profileData.asStateFlow()


    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    suspend fun getProfile(token: String) {
        _uiState.value = UiState.LOADING
        baseRepository.setHeader(token)
        viewModelScope.launch {
            _uiState.value = UiState.LOADING
            try {
                val response = baseRepository.get("users/meta")
                Log.d("User", response)
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

    suspend fun updateProfile(username: String, imageData: ImageData?, context: Context) {
        _uiState.value = UiState.LOADING
        viewModelScope.launch {
            try {
                val formData = mutableListOf<Pair<String, Any>>()
                formData.add("name" to username)

                if (imageData != null && imageData is ImageData.ImageUri) {
                    val file = convertUriToFile(context, imageData.uri)
                    if (file != null) {
                        val response = baseRepository.postFormData("users/update-user", formData, FileDataPart(file, name = "avatar", filename = file.name))
                        if (response.isNotEmpty()) {
                            val result: EditProfileModel = Json.decodeFromString(response)
                            _profileData.value?.name = result.name
                            _profileData.value?.avatar = result.avatar
                        } else {
                            Toast.makeText(context, "Image should be less than 2MB", Toast.LENGTH_SHORT).show()
                        }
                        _uiState.value = UiState.SUCCESS
                    }
                } else {
                    val response = baseRepository.postFormData("users/update-user", formData)
                    if (response.isNotEmpty()) {
                        val result: EditProfileModel = Json.decodeFromString(response)
                        _profileData.value?.name = result.name
                        _uiState.value = UiState.SUCCESS
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.ERROR
                _errorMessage.value = e.message ?: ""
                Log.d("Error API", e.message ?: "")
            }
        }
    }

}