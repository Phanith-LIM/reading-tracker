package com.app.readingtracker.pages.home.get_all_by_category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.home.get_all.GenerateListData

data class GetAllByCategoryView(val id: String, val name: String): Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = viewModel<GetAllByCategoryModel>(factory = GetAllByCategoryViewModelFactory(id))
        val uiState by viewModel.uiState.collectAsState()
        val listBook by viewModel.listBooks.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getAllBooksByCategory()
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
                        GenerateListData(listData = listBook, it = it)
                    }
                }
            }
        )
    }
}



