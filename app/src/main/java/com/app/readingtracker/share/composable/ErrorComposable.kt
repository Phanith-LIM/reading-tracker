package com.app.readingtracker.share.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorComposable(it: PaddingValues, message: String? = null) {
    return Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center,
        content = { Text("An error occurred. Please try again. ${message ?: ""}") }
    )
}