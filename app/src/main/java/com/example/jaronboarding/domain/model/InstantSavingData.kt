package com.example.jaronboarding.domain.model

import androidx.compose.ui.graphics.Color

data class InstantSavingData(
    val toolBarText: String,
    val toolBarIcon: String,
    val introTitle: String,
    val introSubtitle: String,
    val introSubtitleIcon: String,
    val educationCards: List<EducationCard>,
    val saveButton: SaveButtonData,
    val animations: AnimationConfig,
    val ctaLottie: String,
    val actionText: String
)

data class EducationCard(
    val id: Int,
    val imageUrl: String,
    val collapsedText: String,
    val expandedText: String,
    val backgroundColor: Color,
    val strokeStartColor: Color,
    val strokeEndColor: Color,
    val gradientStart: Color,
    val gradientEnd: Color
)

data class SaveButtonData(
    val text: String,
    val backgroundColor: Color,
    val textColor: Color,
    val strokeColor: Color
)

data class AnimationConfig(
    val collapseCardTiltInterval: Long,
    val collapseExpandIntroInterval: Long,
    val bottomToCenterTranslationInterval: Long,
    val expandCardStayInterval: Long
)
