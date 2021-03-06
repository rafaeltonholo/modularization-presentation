package dev.tonholo.awesomeapp.feature.onboard

import androidx.annotation.DrawableRes

data class OnboardPage(
    @DrawableRes
    val resource: Int,
    val title: String,
    val description: String,
)

data class OnboardState(
    val content: List<OnboardPage>,
    val currentPage: Int = 0,
    val targetPage: Int = 0,
    val shouldShowBackButton: Boolean = false,
    val shouldShowSkipButton: Boolean = true,
    val isReadyToShow: Boolean = false,
)
