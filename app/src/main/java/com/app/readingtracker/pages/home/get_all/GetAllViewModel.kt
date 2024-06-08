package com.app.readingtracker.pages.home.get_all

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.readingtracker.core.BaseRepository
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.home.GetAllEnum
import com.app.readingtracker.pages.home.book_detail.Shelve
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("UNCHECKED_CAST")
class  GetAllViewModelFactory(private val type: GetAllEnum) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GetAllViewModel(type) as T
    }
}

class GetAllViewModel(private val type: GetAllEnum): ViewModel() {
    private val _listBooks = MutableStateFlow<List<TreadingBookModel>>(listOf())
    val listBooks: StateFlow<List<TreadingBookModel>> = _listBooks.asStateFlow()

    private val baseRepository = BaseRepository()
    private val _uiState = MutableStateFlow(UiState.LOADING)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _uiUpdateBookState = MutableStateFlow(false)
    val uiUpdateBookState: StateFlow<Boolean> = _uiUpdateBookState.asStateFlow()

    private val _uiAddBookState = MutableStateFlow(false)
    val uiAddBookState: StateFlow<Boolean> = _uiAddBookState.asStateFlow()

    fun getAllBooks(token: String) {
        baseRepository.setHeader(token)
        viewModelScope.launch {
            _uiState.value = UiState.LOADING
            var url = "books/type/${type.displayName}"
            if (type != GetAllEnum.LATEST && type != GetAllEnum.TREADING) {
                url = url.replace(type.displayName, "book-user?type=${type.displayName}")
            }
            Log.d("GET TOKEN", token)
            try {
                val response = baseRepository.get(url)
                _listBooks.value = Json.decodeFromString(response)
                _uiState.value = UiState.SUCCESS
            } catch (e: Exception) {
                _uiState.value = UiState.ERROR
                _errorMessage.value = e.message ?: ""
                Log.d("Error API", e.message ?: "")
            }
        }
    }

    fun onUpdate(value: Shelve?, id: String) {
        _uiUpdateBookState.value = false
        viewModelScope.launch {
            try {
                val body = mapOf(
                    "book_id" to id,
                    "status" to value?.name
                )
                val jsonBody = Json.encodeToString(body)
                Log.d("OnUpdate", jsonBody)
                val response = baseRepository.put("users/my-books/update-status", jsonBody)
                if(response != "") {
                    _uiUpdateBookState.value = true
                    _listBooks.value = _listBooks.value.filterNot { it.id == id }
                }
            } catch (e: Exception) {
                _uiUpdateBookState.value = false
                _errorMessage.value = e.message ?: ""
                Log.d("Error API", e.message ?: "")
            }
        }
    }

    fun onAddToShelve(value: Shelve?, id: String) {
        _uiAddBookState.value = false
        viewModelScope.launch {
            try {
                val body = mapOf(
                    "book_id" to id,
                    "status" to value?.name
                )
                val jsonBody = Json.encodeToString(body)
                Log.d("onSave", jsonBody)
                val response = baseRepository.post("users/my-books/add", jsonBody)
                Log.d("Response", response)
                if(response != "") {
                    _uiAddBookState.value = true
                }
            } catch (e: Exception) {
                _uiAddBookState.value = false
                _errorMessage.value = e.message ?: ""
                Log.d("Error API", e.message ?: "")
            }
        }
    }
}