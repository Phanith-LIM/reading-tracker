package com.app.readingtracker.pages.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kPrimary

@Preview(showBackground = true)
@Composable
fun CircleAvatar(@PreviewParameter(NameProvider::class) name: String, imageUrl: String? = null) {
    if(imageUrl != null) {
        return Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Box(
                    modifier = Modifier.size(96.dp).clip(CircleShape).background(kPrimary.copy(alpha = 0.3f)).aspectRatio(1f),
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = kPrimary,
                    textAlign = TextAlign.Center,
                )
            }
        )
    }

    return Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
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
                        modifier = Modifier.align(Alignment.Center).padding(kPadding / 4).size(48.dp),
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = kPrimary,
                textAlign = TextAlign.Center,
            )
        }
    )
}