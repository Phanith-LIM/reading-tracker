package com.app.readingtracker.pages.profile.edit_profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditProfileModel (
    @SerialName("_id") val id: String,
    val name: String,
    val avatar: String,
    val email: String,
)