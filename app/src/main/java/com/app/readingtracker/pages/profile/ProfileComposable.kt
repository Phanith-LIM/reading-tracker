package com.app.readingtracker.pages.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kPrimary
import com.app.readingtracker.ui.theme.kSpace

@Composable
fun CircleAvatar(name: String? = null, imageUrl: String? = null, email: String? = null) {
    if(imageUrl != null) {
        return ListItem(
            modifier = Modifier.fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            headlineContent = {
                Text(
                    text = email ?: "NAN",
                    style = MaterialTheme.typography.titleMedium,
                    color = kPrimary,
                    textAlign = TextAlign.Center,
                )
            },
            overlineContent = {
                Text(
                    text = name ?: "NAN",
                    style = MaterialTheme.typography.titleLarge,
                    color = kPrimary,
                    textAlign = TextAlign.Center,
                )
            },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(kPrimary.copy(alpha = 0.3f))
                        .aspectRatio(1f),
                    content = {
                        SubcomposeAsyncImage(
                            model = imageUrl,
                            loading = {
                                CircularProgressIndicator()
                            },
                            filterQuality = FilterQuality.Low,
                            clipToBounds = true,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth(),
                            contentDescription = null
                        )
                    }
                )
            },
        )
    }
    return ListItem(
        modifier = Modifier.fillMaxWidth(),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        headlineContent = {
            Text(
                text = "limphanith.dev@gmail.com",
                style = MaterialTheme.typography.bodyMedium,
                color = kPrimary,
                textAlign = TextAlign.Center,
            )
        },
        overlineContent = {
            Text(
                text = name ?: "NAN",
                style = MaterialTheme.typography.titleLarge,
                color = kPrimary,
                textAlign = TextAlign.Center,
            )
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(kPrimary.copy(alpha = 0.3f))
                    .aspectRatio(1f),
                content = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = kPrimary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(kPadding / 4)
                            .size(48.dp),
                    )
                }
            )
        },
    )
}

@Composable
fun UserDataReadingComposable(books: Int, read: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.14f),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.large)
                    .background(Color.Black.copy(0.07f))
                    .padding(kPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = books.toString(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400)
                    )
                    Text(text = "Books", style = MaterialTheme.typography.titleMedium)
                }
            )
            Spacer(modifier = Modifier.width(kSpace))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.large)
                    .background(Color.Black.copy(0.07f))
                    .padding(kPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = read.toString(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400)
                    )
                    Text(text = "Read", style = MaterialTheme.typography.titleMedium)
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun UserDataReadingComposablePreview() {
    return UserDataReadingComposable(books = 100, read = 100)
}