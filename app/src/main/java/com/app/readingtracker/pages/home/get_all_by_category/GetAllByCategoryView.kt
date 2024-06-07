package com.app.readingtracker.pages.home.get_all_by_category

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.home.book_detail.Shelve
import com.app.readingtracker.pages.home.get_all.ListTileBook
import com.app.readingtracker.share.composable.RouteState
import kotlinx.coroutines.flow.firstOrNull

data class GetAllByCategoryView(val id: String, val name: String): Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = viewModel<GetAllByCategoryModel>(factory = GetAllByCategoryViewModelFactory(id))
        val uiState by viewModel.uiState.collectAsState()
        val listBook by viewModel.listBooks.collectAsState()

        val context = LocalContext.current
        val selectedShelve = remember { mutableStateOf<Shelve?>(null) }
        val isAdded by viewModel.uiAddBookState.collectAsState()


        LaunchedEffect(Unit) {
            val token = DataStoreManager.read(context,"refresh").firstOrNull()
            viewModel.getAllBooksByCategory(token)
        }

        LaunchedEffect(isAdded) {
            if (isAdded) {
                Toast.makeText(context, "Added to ${selectedShelve.value}", Toast.LENGTH_SHORT).show()
            }
        }

        return Scaffold(
            modifier = Modifier.background(Color.White),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    title = { Text(text = name) },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigator.pop() },
                            content = {
                                Icon(Icons.Default.ArrowBackIosNew, contentDescription = null)
                            }
                        )
                    }
                )
            },
            content = {
                when(uiState) {
                    UiState.LOADING -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            contentAlignment = Alignment.Center,
                            content = { CircularProgressIndicator() }
                        )
                    }
                    UiState.ERROR -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center,
                            content = { Text("An error occurred. Please try again. ${viewModel.errorMessage}") }
                        )
                    }
                    UiState.SUCCESS -> {
                        LazyColumn(
                            modifier = Modifier.padding(it),
                            content = {
                                items(listBook) {book ->
                                    ListTileBook(
                                        book = book,
                                        routeFrom = RouteState.UPDATE,
                                        selectBook = selectedShelve,
                                        onClick = {
                                            viewModel.onAddToShelve(selectedShelve.value, book.id)
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        )
    }
}



