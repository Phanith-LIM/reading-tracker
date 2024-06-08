package com.app.readingtracker.share.sealed

import android.net.Uri

sealed class ImageData {
    data class ImageUrl(val url: String) : ImageData()
    data class ImageUri(val uri: Uri) : ImageData()
}