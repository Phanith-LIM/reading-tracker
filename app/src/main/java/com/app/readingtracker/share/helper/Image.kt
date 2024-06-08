package com.app.readingtracker.share.helper
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File

fun convertUriToFile(context: Context, uri: Uri): File? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        it.moveToFirst()
        val imagePath = it.getString(columnIndex)
        return File(imagePath)
    }
    return null
}