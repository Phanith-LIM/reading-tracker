package com.app.readingtracker.pages.home.get_all

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.home.GetAllEnum
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

        LaunchedEffect(Unit) {
            val token = DataStoreManager.read(context,"refresh").firstOrNull()
            if(token != null) {
                viewModel.getAllBooks(token)
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
                            content = { Text("An error occurred. Please try again.") }
                        )
                    }
                    UiState.SUCCESS -> {
                        GenerateListData(listData = listData, it = it)
                    }
                }
            }
        )
    }
}



