package com.example.jaronboarding.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InstantSavingResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: DataWrapper
)

data class DataWrapper(
    @SerializedName("manualBuyEducationData")
    val manualBuyEducationData: ManualBuyEducationDataDto
)

data class ManualBuyEducationDataDto(
    @SerializedName("toolBarText")
    val toolBarText: String,
    @SerializedName("introTitle")
    val introTitle: String,
    @SerializedName("introSubtitle")
    val introSubtitle: String,
    @SerializedName("educationCardList")
    val educationCardList: List<EducationCardDto>,
    @SerializedName("saveButtonCta")
    val saveButtonCta: SaveButtonCtaDto,
    @SerializedName("ctaLottie")
    val ctaLottie: String,
    @SerializedName("screenType")
    val screenType: String,
    @SerializedName("cohort")
    val cohort: String,
    @SerializedName("combination")
    val combination: String?,
    @SerializedName("collapseCardTiltInterval")
    val collapseCardTiltInterval: Long,
    @SerializedName("collapseExpandIntroInterval")
    val collapseExpandIntroInterval: Long,
    @SerializedName("bottomToCenterTranslationInterval")
    val bottomToCenterTranslationInterval: Long,
    @SerializedName("expandCardStayInterval")
    val expandCardStayInterval: Long,
    @SerializedName("seenCount")
    val seenCount: Int?,
    @SerializedName("actionText")
    val actionText: String,
    @SerializedName("shouldShowOnLandingPage")
    val shouldShowOnLandingPage: Boolean,
    @SerializedName("toolBarIcon")
    val toolBarIcon: String,
    @SerializedName("introSubtitleIcon")
    val introSubtitleIcon: String,
    @SerializedName("shouldShowBeforeNavigating")
    val shouldShowBeforeNavigating: Boolean
)

data class EducationCardDto(
    @SerializedName("image")
    val image: String,
    @SerializedName("collapsedStateText")
    val collapsedStateText: String,
    @SerializedName("expandStateText")
    val expandStateText: String,
    @SerializedName("backGroundColor")
    val backGroundColor: String,
    @SerializedName("strokeStartColor")
    val strokeStartColor: String,
    @SerializedName("strokeEndColor")
    val strokeEndColor: String,
    @SerializedName("startGradient")
    val startGradient: String,
    @SerializedName("endGradient")
    val endGradient: String
)

data class SaveButtonCtaDto(
    @SerializedName("text")
    val text: String,
    @SerializedName("deeplink")
    val deeplink: String?,
    @SerializedName("backgroundColor")
    val backgroundColor: String,
    @SerializedName("textColor")
    val textColor: String,
    @SerializedName("strokeColor")
    val strokeColor: String,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("order")
    val order: Int?
)