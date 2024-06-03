package com.app.readingtracker.pages.chart

import kotlinx.serialization.Serializable

@Serializable
data class DashboardModel (
    val year: Int,
    val chart: List<LabelModel>
)

@Serializable
data class LabelModel (
    val name: String,
    val value: Float
)
