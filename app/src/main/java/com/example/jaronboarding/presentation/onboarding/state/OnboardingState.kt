package com.example.jaronboarding.presentation.onboarding.state

import com.example.jaronboarding.domain.model.InstantSavingData

data class OnboardingUiState(
    val isLoading: Boolean = false,
    val instantSavingData: InstantSavingData? = null,
    val error: String? = null,
    val animationPhase: AnimationPhase = AnimationPhase.INITIAL,
    val shouldNavigateToLanding: Boolean = false,
    val startCardAnimation: Boolean = false,
    val expandedCardId: Int? = null, // which card is expanded
    val cardAnimationIndex: Int = 0, // current card animating in intro
    val showSaveButton: Boolean = false
)

enum class AnimationPhase {
    INITIAL,
    CARDS_APPEARING,
    ANIMATION_COMPLETE // user interaction phase
}