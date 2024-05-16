package com.app.readingtracker.pages.home.book_detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class BookDetailViewModelFactory(private val id: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookDetailViewModel(id) as T
    }
}

class BookDetailViewModel(private val id: String) : ViewModel() {
    init {
        Log.d("BookDetailViewModel", "ID: $id")
    }
    private val _dataBooks = MutableStateFlow<BookDetailModel?>(null)
    val dataBooks : StateFlow<BookDetailModel?> = _dataBooks.asStateFlow()
    val isLoading = mutableStateOf(true)
    init {
        getBookDetail()
    }

    private fun getBookDetail() {
        val url = "https://reading-tracking-api-99b58363a3cb.herokuapp.com/book/$id"
        val header: HashMap<String, String> = hashMapOf()
        Fuel.get(url).header(header).responseJson { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("BookDetail", "getCategory: ${result.getException()}")
                }
                is Result.Success -> {
                    val data = result.get().content
                    val books = Json.decodeFromString<BookDetailModel>(data)
                    _dataBooks.value = books
                    isLoading.value = false
                }
            }
        }
    }
}