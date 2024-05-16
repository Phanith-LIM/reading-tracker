package com.app.readingtracker.pages.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json


class HomeViewModel: ViewModel() {
    private val _listCategory: MutableStateFlow<List<CategoryModel>> = MutableStateFlow(listOf())
    val listCategory: StateFlow<List<CategoryModel>> = _listCategory.asStateFlow()

    private val _listTreading: MutableStateFlow<List<BookModel>> = MutableStateFlow(listOf())
    val listTreading: StateFlow<List<BookModel>> = _listTreading.asStateFlow()

    private val _listLatest: MutableStateFlow<List<LatestBookModel>> = MutableStateFlow(listOf())
    val listLatest: StateFlow<List<LatestBookModel>> = _listLatest.asStateFlow()

    val isLoading = mutableStateOf(true)

    init {
        getCategory()
        getTreading()
        getLatest()
    }

    private fun getCategory() {
        val url = "https://reading-tracking-api-99b58363a3cb.herokuapp.com/category"
        val header: HashMap<String, String> = hashMapOf()
        Fuel.get(url).header(header).responseJson { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("HomeViewModel", "getCategory: ${result.getException()}")
                }
                is Result.Success -> {
                    val data = result.get().content
                    val categorys = Json.decodeFromString<List<CategoryModel>>(data)
                    _listCategory.value = categorys
                    isLoading.value = false
                }
            }
        }
    }

    private fun getTreading() {
        val url = "https://reading-tracking-api-99b58363a3cb.herokuapp.com/book/type/get_treading"
        val header: HashMap<String, String> = hashMapOf()
        Fuel.get(url).header(header).responseJson { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("HomeViewModel", "getCategory: ${result.getException()}")
                }
                is Result.Success -> {
                    val data = result.get().content
                    println(data)
                    val books = Json.decodeFromString<List<BookModel>>(data)
                    _listTreading.value = books
                }
            }
        }
    }

    private fun getLatest() {
        val url = "https://reading-tracking-api-99b58363a3cb.herokuapp.com/book/type/get_latest"
        val header: HashMap<String, String> = hashMapOf()
        Fuel.get(url).header(header).responseJson { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("HomeViewModel", "getCategory: ${result.getException()}")
                }
                is Result.Success -> {
                    val data = result.get().content
                    val books = Json.decodeFromString<List<LatestBookModel>>(data)
                    _listLatest.value = books
                }
            }
        }
    }
}