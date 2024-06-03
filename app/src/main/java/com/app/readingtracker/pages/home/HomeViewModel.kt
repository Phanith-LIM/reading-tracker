package com.app.readingtracker.pages.home

import com.app.readingtracker.core.BaseRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.readingtracker.core.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class HomeViewModel : ViewModel() {
    private val baseRepository = BaseRepository()
    private val _uiState = MutableStateFlow(UiState.LOADING)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories: StateFlow<List<CategoryModel>> = _categories.asStateFlow()

    private val _treading = MutableStateFlow<List<BookModel>>(emptyList())
    val treading: StateFlow<List<BookModel>> = _treading.asStateFlow()

    private val _latest = MutableStateFlow<List<LatestBookModel>>(emptyList())
    val latest: StateFlow<List<LatestBookModel>> = _latest.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.value = UiState.LOADING
            try {
                val categoryData = baseRepository.get("categories")
                val treadingData = baseRepository.get("books/type/treading")
                val latestData = baseRepository.get("books/type/latest")

                _categories.value = Json.decodeFromString(categoryData)
                _treading.value = Json.decodeFromString(treadingData)
                _latest.value = Json.decodeFromString(latestData)

                _uiState.value = UiState.SUCCESS
            } catch (e: Exception) {
                _uiState.value = UiState.ERROR
                _errorMessage.value = e.message ?: ""
                Log.d("Error API", e.message ?: "")
            }
        }
    }

}
