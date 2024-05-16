package com.app.readingtracker.pages.home.get_all

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.readingtracker.pages.home.GetAllEnum
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class  GetAllViewModelFactory(private val type: GetAllEnum) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GetAllViewModel(type) as T
    }
}

class GetAllViewModel(private val type: GetAllEnum): ViewModel() {
    private val _listBooks = MutableStateFlow<List<TreadingBookModel>>(listOf())
    val listBooks : StateFlow<List<TreadingBookModel>> = _listBooks.asStateFlow()
    val isLoading = mutableStateOf(true)
    init {
        when(type) {
            GetAllEnum.TREADING -> getTreading()
            GetAllEnum.LATEST -> getLasted()
            GetAllEnum.WANT -> getTreading()
            GetAllEnum.READ -> getTreading()
            GetAllEnum.CURRENT -> getTreading()
        }
    }

    private fun getTreading() {
        val url = "https://reading-tracking-api-99b58363a3cb.herokuapp.com/book/type/all_treading"
        val header: HashMap<String, String> = hashMapOf()
        Fuel.get(url).header(header).responseJson { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("Get Treading", "value: ${result.getException()}")
                }
                is Result.Success -> {
                    val data = result.get().content
                    println(data)
                    val listBooks = Json.decodeFromString<List<TreadingBookModel>>(data)
                    _listBooks.value = listBooks
                    isLoading.value = false
                }
            }
        }
    }
    private fun getLasted() {
        val url = "https://reading-tracking-api-99b58363a3cb.herokuapp.com/book/type/all_latest"
        val header: HashMap<String, String> = hashMapOf()
        Fuel.get(url).header(header).responseJson { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("Get Treading", "value: ${result.getException()}")
                }
                is Result.Success -> {
                    val data = result.get().content
                    println(data)
                    val listBooks = Json.decodeFromString<List<TreadingBookModel>>(data)
                    _listBooks.value = listBooks
                    isLoading.value = false
                }
            }
        }
    }
}