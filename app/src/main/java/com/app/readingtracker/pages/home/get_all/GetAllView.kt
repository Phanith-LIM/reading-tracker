package com.app.readingtracker.pages.home.get_all

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.home.GetAllEnum
import com.app.readingtracker.pages.home.book_detail.Shelve
import com.app.readingtracker.share.composable.ErrorComposable
import com.app.readingtracker.share.composable.LoadingComposable
import com.app.readingtracker.share.composable.NoRecordComposable
import com.app.readingtracker.share.composable.RouteState
import kotlinx.coroutines.flow.firstOrNull

data class GetAllView(val typeGet: GetAllEnum): Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = viewModel<GetAllViewModel>(factory = GetAllViewModelFactory(typeGet))
        val uiState by viewModel.uiState.collectAsState()
        val listData by viewModel.listBooks.collectAsState()
        val context = LocalContext.current
        val selectedShelve = remember { mutableStateOf<Shelve?>(null) }
        val isUpdated by viewModel.uiUpdateBookState.collectAsState()
        val isAdded by viewModel.uiAddBookState.collectAsState()
        var myToken by remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            val token = DataStoreManager.read(context,"refresh").firstOrNull()
            if(token != null) {
                viewModel.getAllBooks(token)
                myToken = token
            }
        }

        LaunchedEffect(isUpdated, isAdded) {
            if (isUpdated) {
                Toast.makeText(context, "Updated to ${selectedShelve.value}", Toast.LENGTH_SHORT).show()
            }
            if (isAdded) {
                Toast.makeText(context, "Added to ${selectedShelve.value}", Toast.LENGTH_SHORT).show()
            }
        }
        return Scaffold(
            modifier = Modifier.background(Color.White),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    title = { DynamicText(value = typeGet) },
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
                    UiState.LOADING -> { LoadingComposable(it = it) }
                    UiState.ERROR -> { ErrorComposable(it = it) }
                    UiState.SUCCESS -> {
                        if(listData.isEmpty()) { NoRecordComposable(it = it) }
                        else {
                            LazyColumn(
                                modifier = Modifier.padding(it),
                                content = {
                                    items(listData) {book ->
                                        ListTileBook(
                                            book = book,
                                            routeFrom = if(typeGet == GetAllEnum.LATEST || typeGet == GetAllEnum.TREADING) RouteState.NEW else RouteState.UPDATE,
                                            selectBook = selectedShelve,
                                            onClick = {
                                                if(typeGet == GetAllEnum.LATEST || typeGet == GetAllEnum.TREADING) {
                                                    viewModel.onAddToShelve(selectedShelve.value, book.id)
                                                } else {
                                                    viewModel.onUpdate(selectedShelve.value, book.id)
                                                }
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}



