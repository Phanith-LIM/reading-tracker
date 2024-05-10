package com.app.readingtracker.pages.home.book_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun BookCover(url: String) {
    return Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f).background(Color.Red)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = url,
            loading = {
                Box(
                    modifier = Modifier.width(32.dp).height(32.dp).align(Alignment.Center),
                    content = {
                        CircularProgressIndicator()
                    }
                )
            },
            alpha = 0.8f,
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.Low,
            clipToBounds = true,
            contentDescription = null
        )
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize().size(256.dp).align(Alignment.Center),
            model = url,
            loading = {
                Box(
                    modifier = Modifier.width(32.dp).height(32.dp),
                    content = {
                        CircularProgressIndicator()
                    }
                )
            },
            contentScale = ContentScale.FillHeight,
            filterQuality = FilterQuality.Low,
            clipToBounds = true,
            contentDescription = null
        )
    }
}