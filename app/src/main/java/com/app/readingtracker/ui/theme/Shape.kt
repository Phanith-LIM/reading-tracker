package com.app.readingtracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

val Shape = Shapes(
    extraSmall = RoundedCornerShape(kRadius / 4),
    small = RoundedCornerShape(kRadius / 2),
    medium = RoundedCornerShape(kRadius),
    large = RoundedCornerShape(kRadius * 2),
    extraLarge = RoundedCornerShape(kRadius * 3)
)