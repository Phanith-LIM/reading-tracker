package com.app.readingtracker.pages.home
import kotlinx.serialization.Serializable


enum class GetAllEnum {
    TREADING,
    LATEST,
    READ,
    CURRENT,
    WANT,
}

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