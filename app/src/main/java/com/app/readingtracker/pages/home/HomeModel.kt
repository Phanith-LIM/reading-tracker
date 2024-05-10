package com.app.readingtracker.pages.home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable


data class HomeModel(
    val id: String,
    val name: String,
    val icon: ImageVector
)

@Serializable
data class BookModel(
    val _id: String,
    val title: String,
    val thumbnail: String? = null,
    val average_rating: Double,
)

@Serializable
data class LatestBookModel(
    val _id: String,
    val title: String,
    val thumbnail: String? = null,
    val published_year: Int,
)


@Serializable
data class CategoryModel(
    val _id: String,
    val name: String,
    val icon: String,
)