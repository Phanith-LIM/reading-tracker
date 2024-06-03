package com.app.readingtracker.pages.home.get_all

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.SubcomposeAsyncImage
import com.app.readingtracker.core.blankImage
import com.app.readingtracker.pages.home.GetAllEnum
import com.app.readingtracker.pages.home.book_detail.BookDetailView
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kPrimary
import com.app.readingtracker.ui.theme.kSpace

data class GetAllView(val typeGet: GetAllEnum): Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = viewModel<GetAllViewModel>(factory = GetAllViewModelFactory(typeGet))
        Scaffold(
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
                if (viewModel.isLoading.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (viewModel.listBooks.value.isEmpty()) {
                        Box(
                            modifier = Modifier.padding(it).fillMaxSize(),
                            contentAlignment = Alignment.Center,
                            content = {
                                Text(text = "No records", textAlign = TextAlign.Center)
                            }
                        )
                    } else {
                        GenerateListData(listData = viewModel.listBooks.collectAsState().value, it = it)
                    }
                }
            }
        )
    }
}

@Composable
fun GenerateListData(listData: List<TreadingBookModel>, it: PaddingValues) {
    return LazyColumn(
        modifier = Modifier.padding(it),
        content = {
            items(listData) {book ->
                ListTileBook(book)
            }
        }
    )
}

@Composable
fun DynamicText(value: GetAllEnum) {
    return when(value) {
        GetAllEnum.READ -> Text(text = "Read")
        GetAllEnum.WANT -> Text(text = "Want to Read")
        GetAllEnum.CURRENT -> Text(text = "Current Reading")
        GetAllEnum.LATEST -> Text(text = "Latest")
        GetAllEnum.TREADING -> Text(text = "Treading")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTileBook(book: TreadingBookModel) {
    val navigator = LocalNavigator.currentOrThrow
    return Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(kPadding / 2),
        onClick = { navigator.push(BookDetailView(id= book._id)) },
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.background,
        ),
        content = {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                leadingContent = {
                    SubcomposeAsyncImage(
                        modifier = Modifier.height(150.dp).width(100.dp),
                        model = book.thumbnail ?: blankImage,
                        loading = {
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                                    .align(Alignment.Center),
                                content = {
                                    CircularProgressIndicator()
                                }
                            )
                        },
                        filterQuality = FilterQuality.Low,
                        clipToBounds = true,
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = book.title) },
                supportingContent = {
                    Text(
                        buildAnnotatedString {
                            append("by")
                            append(" ")
                            withStyle(style = SpanStyle(color = kPrimary)) {
                                append(text = book.authors)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(kSpace))
                    AssistChip(modifier = Modifier
                        .height(40.dp)
                        .padding(end = 8.dp),
                        label = { Text( book.categories ?: "NAN") },
                        border = AssistChipDefaults.assistChipBorder(
                            borderWidth = 0.dp,
                            borderColor = Color.Transparent
                        ),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = kPrimary.copy(alpha = 0.2f)
                        ),
                        shape = MaterialTheme.shapes.medium,
                        enabled = true,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.height(kSpace))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Text(text = if(book.num_pages != null) book.num_pages.toString() + " pages" else "NAN")
                            MenuSample()
                        }
                    )
                },
            )
        }
    )
}

