package com.app.readingtracker.share.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingComposable(it: PaddingValues) {
    return Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(it),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() }
    )
}