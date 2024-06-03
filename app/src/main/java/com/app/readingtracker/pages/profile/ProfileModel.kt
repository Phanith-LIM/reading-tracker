package com.app.readingtracker.pages.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel (
    val read: Int,
    val books: Int,
    val name: String,
    val avatar: String,
)