package com.app.readingtracker.pages.home.book_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class Shelve {
    READ,
    CURRENT,
    WANT
}

@Serializable
data class BookDetailModel (
    @SerialName("_id") val _id: String,
    val title: String,
    val isbn13: Long? = null,
    val authors: String? = null,
    val categories: String? = null,
    val thumbnail: String? = null,
    @SerialName("average_rating") val averageRating: Double? = null,
    @SerialName("published_year") val publishedYear: Int? = null,
    @SerialName("num_pages") val numPages: Int? = null,
    @SerialName("ratings_count") val ratingsCount: Int? = null
)
