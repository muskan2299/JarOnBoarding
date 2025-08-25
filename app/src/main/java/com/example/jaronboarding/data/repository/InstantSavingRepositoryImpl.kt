package com.example.jaronboarding.data.repository

import androidx.compose.ui.graphics.Color
import com.example.jaronboarding.data.remote.ApiService
import com.example.jaronboarding.data.remote.dto.ManualBuyEducationDataDto
import com.example.jaronboarding.domain.model.AnimationConfig
import com.example.jaronboarding.domain.model.EducationCard
import com.example.jaronboarding.domain.model.InstantSavingData
import com.example.jaronboarding.domain.model.SaveButtonData
import com.example.jaronboarding.domain.repository.InstantSavingRepository
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.graphics.toColorInt

@Singleton
class InstantSavingRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : InstantSavingRepository {

    override suspend fun getInstantSavingData(): Result<InstantSavingData> = try {
        val response = apiService.getInstantSavingData()
        Result.success(response.data.manualBuyEducationData.toInstantSavingData())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun ManualBuyEducationDataDto.toInstantSavingData(): InstantSavingData {
    return InstantSavingData(
        toolBarText = toolBarText,
        toolBarIcon = toolBarIcon,
        introTitle = introTitle,
        introSubtitle = introSubtitle,
        introSubtitleIcon = introSubtitleIcon,
        educationCards = educationCardList.mapIndexed { index, cardDto ->
            EducationCard(
                id = index,
                imageUrl = cardDto.image,
                collapsedText = cardDto.collapsedStateText,
                expandedText = cardDto.expandStateText,
                backgroundColor = Color(cardDto.backGroundColor.toColorInt()),
                strokeStartColor = Color(cardDto.strokeStartColor.toColorInt()),
                strokeEndColor = Color(cardDto.strokeEndColor.toColorInt()),
                gradientStart = Color(cardDto.startGradient.toColorInt()),
                gradientEnd = Color(cardDto.endGradient.toColorInt())
            )
        },
        saveButton = SaveButtonData(
            text = saveButtonCta.text,
            backgroundColor = Color(saveButtonCta.backgroundColor.toColorInt()),
            textColor = Color(saveButtonCta.textColor.toColorInt()),
            strokeColor = Color(saveButtonCta.strokeColor.toColorInt())
        ),
        animations = AnimationConfig(
            collapseCardTiltInterval = collapseCardTiltInterval,
            collapseExpandIntroInterval = collapseExpandIntroInterval,
            bottomToCenterTranslationInterval = bottomToCenterTranslationInterval,
            expandCardStayInterval = expandCardStayInterval
        ),
        ctaLottie = ctaLottie,
        actionText = actionText
    )
}