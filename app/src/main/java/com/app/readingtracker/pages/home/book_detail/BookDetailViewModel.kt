package com.app.readingtracker.pages.home.book_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.readingtracker.core.BaseRepository
import com.app.readingtracker.core.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("UNCHECKED_CAST")
class BookDetailViewModelFactory(private val id: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookDetailViewModel(id) as T
    }
}

class BookDetailViewModel(private val id: String) : ViewModel() {

    private val _dataBooks = MutableStateFlow<BookDetailModel?>(null)
    val dataBooks : StateFlow<BookDetailModel?> = _dataBooks.asStateFlow()


    private val baseRepository = BaseRepository()
    private val _uiState = MutableStateFlow(UiState.LOADING)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    init {
        getBookDetail()
    }

    private fun getBookDetail() {
        viewModelScope.launch {
           try {
               _uiState.value = UiState.LOADING
               val response = baseRepository.get("books/${id}")
               _dataBooks.value = Json.decodeFromString(response)
               _uiState.value = UiState.SUCCESS
           } catch (e: Exception) {
               _uiState.value = UiState.ERROR
               _errorMessage.value = e.message ?: ""
               Log.d("Error API", e.message ?: "")
           }
       }
    }

    fun onSetToken(token: String) {
        baseRepository.setHeader(token)
    }


    fun onSave(value: Shelve?) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.LOADING
                val body = mapOf(
                    "book_id" to id,
                    "status" to value?.name
                )
                val jsonBody = Json.encodeToString(body)
                Log.d("OnSave", jsonBody)
                val response = baseRepository.post("users/my-books/add", jsonBody)
                if(response != "") {
                    _uiState.value = UiState.SUCCESS
                }
            } catch (e: Exception) {
                _uiState.value = UiState.ERROR
                _errorMessage.value = e.message ?: ""
                Log.d("Error API", e.message ?: "")
            }
        }
    }

    fun onUpdate(value: Shelve?) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.LOADING
                val body = mapOf(
                    "book_id" to id,
                    "status" to value?.name
                )
                val jsonBody = Json.encodeToString(body)
                Log.d("OnUpdate", jsonBody)
                val response = baseRepository.put("users/my-books/update-status", jsonBody)
                if(response != "") {
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