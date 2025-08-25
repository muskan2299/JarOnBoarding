package com.example.jaronboarding.presentation.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jaronboarding.domain.usecase.GetInstantSavingDataUseCase
import com.example.jaronboarding.presentation.onboarding.state.AnimationPhase
import com.example.jaronboarding.presentation.onboarding.state.OnboardingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getInstantSavingDataUseCase: GetInstantSavingDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        loadInstantSavingData()
    }

    private fun loadInstantSavingData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getInstantSavingDataUseCase()
                .onSuccess { data ->
                    Log.d("OnboardingViewModel-Log","Data loaded: $data")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        instantSavingData = data,
                        error = null
                    )
                    startCardAnimation()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
        }
    }

    private fun startCardAnimation() {
        viewModelScope.launch {
            delay(500)
            _uiState.update {
                it.copy(
                    animationPhase = AnimationPhase.CARDS_APPEARING,
                    startCardAnimation = true,
                    cardAnimationIndex = 0
                )
            }
        }
    }

    fun onCardClicked(cardId: Int) {
        if(cardId==-1) return
        _uiState.update { state ->
            state.copy(
                expandedCardId = cardId
            )
        }
    }

    fun onSaveButtonClicked() {
        _uiState.value = _uiState.value.copy(shouldNavigateToLanding = true)
    }

    fun onNavigationEventHandled() {
        _uiState.value = _uiState.value.copy(shouldNavigateToLanding = false)
    }

    fun retryLoading() {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = null,
            startCardAnimation = false,
            animationPhase = AnimationPhase.INITIAL,
            expandedCardId = null,
            cardAnimationIndex = 0
        )
        loadInstantSavingData()
    }
}



