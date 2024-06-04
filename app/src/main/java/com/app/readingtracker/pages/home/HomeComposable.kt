package com.app.readingtracker.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import coil.compose.SubcomposeAsyncImage
import com.app.readingtracker.pages.home.book_detail.BookDetailView
import com.app.readingtracker.pages.home.get_all_by_category.GetAllByCategoryView
import com.app.readingtracker.share.composable.BookCard
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kPrimary
import com.app.readingtracker.ui.theme.kSpace

@Composable
fun HeaderTile(label: String, subLabel: String? = null, onClick: () -> Unit) {
    if(subLabel != null) {
        return Column (
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = {
                        Text(label, style = MaterialTheme.typography.titleLarge)
                        Text(
                            "view all",
                            modifier = Modifier.clickable { onClick() },
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                )
                Spacer(modifier = Modifier.height(kSpace / 2))
                Text(subLabel, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            }
        )
    }
    return Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Text(label, style = MaterialTheme.typography.titleLarge)
        }
    )
}


@Composable
fun ListGenerator(list: List<CategoryModel>?, navigator: Navigator?) {
    LazyRow {
        items(list ?: emptyList()) { category ->
            AssistChip(
                modifier = Modifier
                    .height(40.dp)
                    .padding(end = 8.dp),
                label = { Text(text = category.name) },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(kPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        IconByName(category.icon)
                    }
                },
                shape = MaterialTheme.shapes.large,
                enabled = true,
                onClick = {
                    navigator?.push(GetAllByCategoryView(category.id, category.name))
                }
            )
        }
    }
}

@Composable
fun ListSearch(list: List<SearchModel>, navigator: Navigator?) {
    return Column {
        list.forEach { searchModel ->
            Row(
                modifier = Modifier.padding(kPadding / 2),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Box(
                        modifier = Modifier.height(64.dp).width(32.dp),
                        content = {
                            SubcomposeAsyncImage(
                                model = searchModel.thumbnail ?: "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/991px-Placeholder_view_vector.svg.png",
                                loading = {
                                    Box(
                                        modifier = Modifier.width(32.dp).height(32.dp).align(Alignment.Center),
                                        content = {
                                            CircularProgressIndicator()
                                        }
                                    )
                                },
                                filterQuality = FilterQuality.Low,
                                clipToBounds = true,
                                modifier = Modifier.fillMaxWidth(),
                                contentDescription = null
                            )
                        }
                    )
                    Text(
                        text = searchModel.title,
                        modifier = Modifier.padding( start = kPadding)
                    )
                }
            )
        }
    }
}

@Composable
fun IconByName(name: String) {
    val icon: ImageVector? = remember(name) {
        try {
            val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
            val method = cl.declaredMethods.first()
            method.invoke(null, Icons.Default) as ImageVector
        } catch (_: Throwable) {
            null
        }
    }
    if (icon != null) {
        Icon(icon, "$name icon",  modifier = Modifier.size(16.dp), tint = Color.White)
    }
}


@Composable
fun ListGenerateBook(list: List<BookModel> ?, navigator: Navigator?) {
    return LazyRow {
        items(list ?: emptyList()) { book ->
            BookCard(
                bookName = book.title,
                bookImage = book.thumbnail ?: "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/991px-Placeholder_view_vector.svg.png",
                onClick = {
                    navigator?.push(BookDetailView(book.id))
                }
            )
        }
    }
}


@Composable
fun ListGenerateLatestBook(list: List<LatestBookModel> ?,  navigator: Navigator?) {
    return LazyRow {
        items(list ?: emptyList()) { book ->
            BookCard(
                bookName = book.title,
                bookImage = book.thumbnail ?: "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/991px-Placeholder_view_vector.svg.png",
                onClick = {
                    navigator?.push(BookDetailView(book.id))
                }
            )
        }
    }
}