package com.app.readingtracker.pages.profile.edit_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.app.readingtracker.share.sealed.ImageData

@Composable
fun CircleImageWithIconAndFilePicker(imageData: ImageData, onFilePick: () -> Unit, modifier: Modifier = Modifier, shape: Shape = CircleShape) {
    Box(
        modifier = modifier.clip(shape).aspectRatio(1f).clickable(onClick = onFilePick),
        content = {
            when (imageData) {
                is ImageData.ImageUrl -> {
                    SubcomposeAsyncImage(
                        model = imageData.url,
                        loading = {
                            CircularProgressIndicator()
                        },
                        filterQuality = FilterQuality.Low,
                        clipToBounds = true,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )
                }
                is ImageData.ImageUri -> {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageData.uri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.3f), shape = shape),
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        text = "Tap to change",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    )
}

@Composable
fun MyTextField(text: String, onTextChanged: (String) -> Unit) {
    return OutlinedTextField(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.10f),
        value = text,
        onValueChange = { it -> onTextChanged(it) },
        label = { Text("Username") },
        maxLines = 1,
        shape = MaterialTheme.shapes.medium,
        singleLine = true
    )
}
