package com.app.readingtracker.pages.book

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.home.GetAllEnum
import com.app.readingtracker.pages.home.get_all.GetAllView
import com.app.readingtracker.share.composable.ErrorComposable
import com.app.readingtracker.share.composable.LoadingComposable
import com.app.readingtracker.ui.theme.kPadding
import kotlinx.coroutines.flow.firstOrNull

@OptIn(ExperimentalMaterial3Api::class)
data class BookView(val navigator: Navigator?): Screen {
    @Composable
    override fun Content() {
        val viewModel = viewModel<BookViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        val listBookCount by viewModel.bookCount.collectAsState()

        LaunchedEffect(Unit) {
            val token = DataStoreManager.read(context,"refresh").firstOrNull()
            if(token != null) {
                viewModel.getBooks(token)
            }
        }
        return Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                    title = { Text("Books", textAlign = TextAlign.Center) },
                )
            },
            content = { it ->
                when(uiState) {
                    UiState.LOADING -> { LoadingComposable(it = it) }
                    UiState.ERROR -> { ErrorComposable(it = it) }
                    UiState.SUCCESS -> {
                        Column (
                            modifier = Modifier.fillMaxSize().padding(it).padding(kPadding * 2).verticalScroll(rememberScrollState()),
                            content = {
                                Text("Shelves", style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(kPadding / 2 ))
                                ListTile(
                                    title = listBookCount[0].name,
                                    subTitle = "${listBookCount[0].count} books",
                                    icon = Icons.Filled.Book,
                                    onClick = {
                                        navigator?.push(GetAllView(GetAllEnum.WANT))
                                    }
                                )
                                Spacer(modifier = Modifier.height(kPadding /2 ))
                                ListTile(
                                    title = listBookCount[1].name,
                                    subTitle = "${listBookCount[1].count} books",
                                    icon = Icons.Filled.MyLocation,
                                    onClick = {
                                        navigator?.push(GetAllView(GetAllEnum.CURRENT))
                                    }
                                )
                                Spacer(modifier = Modifier.height(kPadding / 2))
                                ListTile(
                                    title = listBookCount[2].name,
                                    subTitle = "${listBookCount[2].count} books",
                                    icon = Icons.Filled.CheckCircle,
                                    onClick = {
                                        navigator?.push(GetAllView(GetAllEnum.READ))
                                    }
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}


@Composable
fun ListTile(title: String, subTitle: String, icon: ImageVector, onClick: () -> Unit) {
    return ListItem(
        headlineContent = { Text(title) },
        modifier = Modifier
            .padding(vertical = kPadding)
            .border(BorderStroke(width = 1.dp, color = Color.LightGray), shape = MaterialTheme.shapes.large)
            .height(100.dp)
            .clickable(onClick = onClick),
        supportingContent = { Text(subTitle) },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        leadingContent = {
            Icon(
                icon,
                modifier = Modifier.size(40.dp),
                contentDescription = "Localized description",
            )
        }
    )
}