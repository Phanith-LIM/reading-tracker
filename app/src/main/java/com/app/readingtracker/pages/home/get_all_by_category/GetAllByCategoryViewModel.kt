package com.app.readingtracker.pages.home.get_all_by_category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.readingtracker.core.BaseRepository
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.home.book_detail.Shelve
import com.app.readingtracker.pages.home.get_all.TreadingBookModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("UNCHECKED_CAST")
class  GetAllByCategoryViewModelFactory(private val id: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GetAllByCategoryModel(id) as T
    }
}

class GetAllByCategoryModel(private val id: String): ViewModel() {
    private val _listBooks = MutableStateFlow<List<TreadingBookModel>>(listOf())
    val listBooks: StateFlow<List<TreadingBookModel>> = _listBooks.asStateFlow()

    private val baseRepository = BaseRepository()
    private val _uiState = MutableStateFlow(UiState.LOADING)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _uiAddBookState = MutableStateFlow<Boolean>(false)
    val uiAddBookState: StateFlow<Boolean> = _uiAddBookState.asStateFlow()


    fun getAllBooksByCategory(token: String?) {
        baseRepository.setHeader(token ?: "")
        viewModelScope.launch {
            _uiState.value = UiState.LOADING
            try {
                val response = baseRepository.get("books/category/${id}/books")
                _listBooks.value = Json.decodeFromString(response)
                _uiState.value = UiState.SUCCESS
            } catch (e: Exception) {
                _uiState.value = UiState.ERROR
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
                if(response.isNotEmpty()) {
                    _uiAddBookState.value = true
                    Log.d("onSave", response)
                }
            } catch (e: Exception) {
                _uiAddBookState.value = false
                _errorMessage.value = e.message ?: ""
                Log.d("Error API", e.message ?: "")
            }
        }
    }
}