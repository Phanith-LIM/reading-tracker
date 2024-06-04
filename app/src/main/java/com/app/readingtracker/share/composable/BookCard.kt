package com.app.readingtracker.share.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.app.readingtracker.ui.theme.kPadding

@Composable
fun BookCard(bookName: String, bookImage: String, onClick: () -> Unit) {
    return Card(
        modifier = Modifier.width(150.dp).height(260.dp).clickable(onClick = onClick).padding(kPadding / 2),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.background,
        ),
        content = {
            Column(modifier = Modifier.padding(kPadding)) {
                Box(
                    modifier = Modifier.clip(MaterialTheme.shapes.medium).weight(1f),
                    contentAlignment = Alignment.Center,
                    content = {
                        SubcomposeAsyncImage(
                            model = bookImage,
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
                    text = bookName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = kPadding),
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    maxLines = 1, // Limit to one line
                )
            }
        }
    )
}