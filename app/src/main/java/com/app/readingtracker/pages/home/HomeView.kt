package com.app.readingtracker.pages.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.home.get_all.GetAllView
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kSpace
import kotlinx.coroutines.flow.firstOrNull

class HomeView(val navigator: Navigator?) : Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var active by rememberSaveable { mutableStateOf(false) }
        var text by rememberSaveable { mutableStateOf("") }
        val viewModel = viewModel<HomeViewModel>()
        val context = LocalContext.current

        val uiState by viewModel.uiState.collectAsState()
        val categories by viewModel.categories.collectAsState()
        val treading by viewModel.treading.collectAsState()
        val latest by viewModel.latest.collectAsState()
        val error by viewModel.errorMessage.collectAsState()

        LaunchedEffect(Unit) {
            val data = DataStoreManager.read(context, "value")
            Log.d("Storage", data.firstOrNull().toString())
        }

        return Scaffold(
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = if (active) 0.dp else kPadding),
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = {},
                        active = active,
                        onActiveChange = {
                            active = it
                        },
                        placeholder = { Text("Title, author or ISBN") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { active = !active }) {
                                Icon(
                                    if (!active) Icons.Default.MoreVert else Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        },
                        content = {

                        }
                    )
                }
            },
            content = { it ->
                when (uiState){
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
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .padding(kPadding * 2)
                                .verticalScroll(rememberScrollState()),
                            content = {
                                // Categories
                                HeaderTile(label = "Categories") {}
                                Spacer(modifier = Modifier.height(8.dp))
                                ListGenerator(categories.subList(0, categories.size / 2))
                                Spacer(modifier = Modifier.height(8.dp))
                                ListGenerator(categories.subList(categories.size / 2, categories.size))
                                Spacer(modifier = Modifier.height(kSpace * 3))

                                // Treading
                                HeaderTile(
                                    label = "Treading",
                                    subLabel = "What's popular now",
                                    onClick = {
                                        navigator?.push(GetAllView(GetAllEnum.TREADING))
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                ListGenerateBook(treading, navigator = navigator)
                                Spacer(modifier = Modifier.height(kSpace * 3))

                                // Latest
                                HeaderTile(
                                    label = "Latest",
                                    subLabel = "Titles recently added on",
                                    onClick = {
                                        navigator?.push(GetAllView(GetAllEnum.LATEST))
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                ListGenerateLatestBook(latest, navigator = navigator)
                                Spacer(modifier = Modifier.height(kSpace * 3))
                            }
                        )
                    }
                }
            }
        )
    }
}