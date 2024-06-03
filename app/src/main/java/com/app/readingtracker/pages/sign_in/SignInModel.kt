package com.app.readingtracker.pages.sign_in
import kotlinx.serialization.Serializable

@Serializable
data class TokenModel(
    val accessToken: String,
    val refreshToken: String,
    val type: String,
)