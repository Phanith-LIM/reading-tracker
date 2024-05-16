package com.app.readingtracker.pages.home.get_all

import kotlinx.serialization.Serializable

@Serializable
data class TreadingBookModel(
    val _id: String,
    val title: String,
    val authors: String? = null,
    val categories: String? = null,
    val thumbnail: String? = null,
    val average_rating: Double? = null,
    val published_year: Int? = null,
    val num_pages: Int ? = null,
)