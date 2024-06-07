package com.app.readingtracker.pages.home.get_all

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.SubcomposeAsyncImage
import com.app.readingtracker.core.blankImage
import com.app.readingtracker.pages.home.GetAllEnum
import com.app.readingtracker.pages.home.book_detail.BookDetailView
import com.app.readingtracker.pages.home.book_detail.Shelve
import com.app.readingtracker.share.composable.RouteState
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kPrimary
import com.app.readingtracker.ui.theme.kSpace

@Composable
fun MenuSample(selectBook: MutableState<Shelve?>, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopStart),
        content = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    DropdownMenuItem(
                        text = { Text("Want to Read") },
                        onClick = {
                            selectBook.value = Shelve.WANT
                            onClick()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text("Currently Reading") },
                        onClick = {
                            selectBook.value = Shelve.CURRENT
                            onClick()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text("Read") },
                        onClick = {
                            selectBook.value = Shelve.READ
                            onClick()
                        },
                    )
                }
            )
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
fun ListTileBook(book: TreadingBookModel, routeFrom: RouteState,selectBook: MutableState<Shelve?>, onClick: () -> Unit) {
    val navigator = LocalNavigator.currentOrThrow
    return Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(kPadding / 2),
        onClick = { navigator.push(BookDetailView(id= book.id, routeFrom)) },
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
                        modifier = Modifier
                            .height(150.dp)
                            .width(100.dp),
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
                            Text(text = if(book.numPages != null) book.numPages.toString() + " pages" else "NAN")
                            MenuSample(selectBook, onClick = onClick)
                        }
                    )
                },
            )
        }
    )
}