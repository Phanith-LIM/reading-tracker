package com.app.readingtracker.pages.home
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class GetAllEnum(val displayName: String) {
    TREADING("treading-list"),
    LATEST("latest-list"),
    READ("READ"),
    CURRENT("CURRENT"),
    WANT("WANT"),
}

@Serializable
data class BookModel(
    @SerialName("_id") val id: String,
    val title: String,
    val thumbnail: String? = null,
    @SerialName("average_rating") val averageRating: Double
)

@Serializable
data class LatestBookModel(
    @SerialName("_id") val id: String,
    val title: String,
    val thumbnail: String? = null,
    @SerialName("published_year") val publishedYear: Int,
)

@Serializable
data class CategoryModel(
    @SerialName("_id") val id: String,
    val name: String,
    val icon: String,
)

@Serializable
data class SearchModel(
    @SerialName("_id") val id: String,
    val title: String,
    val thumbnail: String?,
)