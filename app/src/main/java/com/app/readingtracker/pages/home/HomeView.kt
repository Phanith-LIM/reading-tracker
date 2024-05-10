package com.app.readingtracker.pages.home

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kSpace

class HomeView(val navigator: Navigator?): Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var active by rememberSaveable { mutableStateOf(false) }
        var text by rememberSaveable { mutableStateOf("") }
        val viewModel = viewModel<HomeViewModel>()

        return Scaffold (
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                Box(modifier = Modifier.fillMaxWidth(),) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth().padding(
                            horizontal = if(active) 0.dp else kPadding,
                        ),
                        query = text,
                        onQueryChange = {
                            text = it
                        },
                        onSearch = {},
                        active = active,
                        onActiveChange = {
                            active = it
                        },
                        placeholder = { Text("Title, author or ISBN") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { active = !active }) {
                                Icon(if(!active) Icons.Default.MoreVert else Icons.Default.Close, contentDescription = null)
                            }
                        }
                    ) {

                    }
                }
            },
            content = { it ->
                if(viewModel.isLoading.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {

                    Column (
                        modifier = Modifier.fillMaxSize().padding(it).padding(kPadding * 2).verticalScroll(rememberScrollState()),
                    ) {
                        // Categories
                        HeaderTile(label = "Categories") { }
                        Spacer(modifier = Modifier.height(8.dp))
                        ListGenerator(viewModel.listCategory.collectAsState().value.subList(0, viewModel.listCategory.collectAsState().value.size / 2))
                        Spacer(modifier = Modifier.height(8.dp))
                        ListGenerator(viewModel.listCategory.collectAsState().value.subList(viewModel.listCategory.collectAsState().value.size / 2, viewModel.listCategory.collectAsState().value.size))
                        Spacer(modifier = Modifier.height(kSpace * 3))

                        // Treading
                        HeaderTile(label = "Treading", subLabel = "What's popular now") { }
                        Spacer(modifier = Modifier.height(8.dp))
                        ListGenerateBook(viewModel.listTreading.collectAsState().value, navigator = navigator)
                        Spacer(modifier = Modifier.height(kSpace * 3))

                        // Latest
                        HeaderTile(label = "Latest", subLabel = "Titles recently added on") { }
                        Spacer(modifier = Modifier.height(8.dp))
                        ListGenerateLatestBook(viewModel.listLatest.collectAsState().value)
                        Spacer(modifier = Modifier.height(kSpace * 3))
                    }
                }
            }
        )
    }
}
