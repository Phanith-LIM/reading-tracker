package com.app.readingtracker.pages.home.get_all

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TreadingBookModel(
    @SerialName("_id") val id: String,
    val title: String,
    val authors: String? = null,
    val categories: String? = null,
    val thumbnail: String? = null,
    @SerialName("average_rating") val averageRating: Double? = null,
    @SerialName("published_year") val publishedYear: Int? = null,
    @SerialName("num_pages") val numPages: Int ? = null,
)