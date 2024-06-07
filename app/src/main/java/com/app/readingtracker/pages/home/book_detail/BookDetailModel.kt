package com.app.readingtracker.pages.home.book_detail

import kotlinx.serialization.Serializable

enum class Shelve {
    READ,
    CURRENT,
    WANT
}

@Serializable
data class BookDetailModel (
    val _id: String,
    val title: String,
    val isbn13: Long? = null, // Change the type to Long?
    val authors: String? = null,
    val categories: String? = null,
    val thumbnail: String? = null,
    val average_rating: Double? = null,
    val published_year: Int? = null,
    val num_pages: Int? = null,
    val ratings_count: Int? = null
)
