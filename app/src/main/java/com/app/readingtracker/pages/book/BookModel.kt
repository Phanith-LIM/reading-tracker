package com.app.readingtracker.pages.book

import kotlinx.serialization.Serializable

@Serializable
data class BookModelCount (
    val name: String,
    val count: Int
)

