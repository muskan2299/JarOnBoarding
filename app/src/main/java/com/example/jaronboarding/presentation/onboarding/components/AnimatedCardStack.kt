package com.example.jaronboarding.presentation.onboarding.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.jaronboarding.domain.model.InstantSavingData
import com.example.jaronboarding.presentation.onboarding.state.AnimationPhase
import com.example.jaronboarding.presentation.onboarding.state.OnboardingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//@OptIn(ExperimentalSharedTransitionApi::class)
//@Composable
//fun AnimatedCardStack(
//    data: InstantSavingData,
//    uiState: OnboardingUiState,
//    onCardClicked: (Int) -> Unit,
//    onSaveClicked: () -> Unit
//) {
//    var animationPhase by remember { mutableStateOf(AnimationPhase.INITIAL) }
//    var expandedCardId by rememberSaveable { mutableStateOf<Int?>(null) }
//    var appearedCardIds by remember { mutableStateOf(setOf<Int>()) }
//    var collapsedCardTilt by remember { mutableStateOf<Map<Int, Float>>(emptyMap()) }
//
//    // sequential expansion / collapse logic
//    LaunchedEffect(Unit) {
//        data.educationCards.forEachIndexed { index, card ->
//            appearedCardIds = appearedCardIds + card.id
//            expandedCardId = card.id
//            onCardClicked(card.id)
//
//            if (card.id != data.educationCards.last().id) {
//                delay(data.animations.expandCardStayInterval)
//
//                // collapse tilt
//                val tilt = if (index % 2 == 0) -8f else 8f
//                collapsedCardTilt = collapsedCardTilt + (card.id to tilt)
//                expandedCardId = null
//
//                delay(data.animations.collapseExpandIntroInterval)
//
//                // straighten
//                collapsedCardTilt = collapsedCardTilt + (card.id to 0f)
//            }
//        }
//        animationPhase = AnimationPhase.ANIMATION_COMPLETE
//    }
//
//    SharedTransitionLayout {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//                    .padding(top = 8.dp, bottom = 24.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                data.educationCards.forEach { card ->
//                    val isExpanded = expandedCardId == card.id
//                    val hasAppeared = appearedCardIds.contains(card.id)
//
//                    // Slide from bottom
//                    val offsetY by animateDpAsState(
//                        targetValue = if (hasAppeared) 0.dp else 800.dp,
//                        animationSpec = tween(
//                            durationMillis = data.animations.bottomToCenterTranslationInterval.toInt(),
//                            easing = FastOutSlowInEasing
//                        )
//                    )
//
//                    // Animate tilt
//                    val rotationZee by animateFloatAsState(
//                        targetValue = collapsedCardTilt[card.id] ?: 0f,
//                        animationSpec = tween(
//                            durationMillis = data.animations.collapseCardTiltInterval.toInt(),
//                            easing = LinearOutSlowInEasing
//                        )
//                    )
//
//                    AnimatedContent(
//                        targetState = isExpanded,
//                        label = "CardExpandCollapse"
//                    ) { expanded ->
//                        EducationCard(
//                            card = card,
//                            isExpanded = expanded,
//                            modifier = Modifier
//                                .graphicsLayer {
//                                    translationY = offsetY.toPx()
//                                    rotationZ = rotationZee
//                                }
//                                .clickable(enabled = animationPhase == AnimationPhase.ANIMATION_COMPLETE) {
//                                    expandedCardId = card.id
//                                    onCardClicked(card.id)
//                                },
//                            sharedTransitionScope = this@SharedTransitionLayout,
//                            animatedVisibilityScope = this
//                        )
//                    }
//                }
//            }
//
//            // Save Button
//            if (animationPhase == AnimationPhase.ANIMATION_COMPLETE) {
//                SaveButton(data = data, onSaveClicked = onSaveClicked, modifier = Modifier.align(Alignment.BottomCenter))
//            }
//        }
//    }
//}
//
//@Composable
//private fun SaveButton(data: InstantSavingData, onSaveClicked: () -> Unit, modifier: Modifier) {
//    Button(
//        onClick = onSaveClicked,
//        modifier = modifier
//            .padding(16.dp)
//            .wrapContentWidth()
//            .height(56.dp),
//        shape = RoundedCornerShape(28.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = data.saveButton.backgroundColor
//        ),
//        border = BorderStroke(1.dp, data.saveButton.strokeColor)
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(
//                text = data.saveButton.text,
//                style = MaterialTheme.typography.titleMedium,
//                color = data.saveButton.textColor
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            val composition by rememberLottieComposition(
//                LottieCompositionSpec.Url(data.ctaLottie)
//            )
//            LottieAnimation(
//                composition = composition,
//                modifier = Modifier.size(32.dp),
//                iterations = LottieConstants.IterateForever
//            )
//        }
//    }
//}

@Composable
fun AnimatedCardStack(
    data: InstantSavingData,
    uiState: OnboardingUiState,
    onCardClicked: (Int) -> Unit,
    onSaveClicked: () -> Unit
) {
    var animationPhase by remember { mutableStateOf(AnimationPhase.INITIAL) }
    var expandedCardId by rememberSaveable { mutableStateOf<Int?>(null) }
    var appearedCardIds by remember { mutableStateOf(setOf<Int>()) }
    var collapsedCardTilt by remember { mutableStateOf<Map<Int, Float>>(emptyMap()) }

    LaunchedEffect(Unit) {
        data.educationCards.forEachIndexed { index, card ->
            appearedCardIds = appearedCardIds + card.id
            expandedCardId = card.id
            onCardClicked(card.id)

            if(card.id != data.educationCards.last().id) {
                    delay(data.animations.expandCardStayInterval) // stay expanded

                    // Collapse: tilt left/right, then straighten
                    val tilt = if (index % 2 == 0) -8f else 8f
                    collapsedCardTilt = collapsedCardTilt + (card.id to tilt)
                    expandedCardId = null

                    delay(data.animations.collapseExpandIntroInterval)

                    // Straighten back
                this.launch {
                    delay(data.animations.bottomToCenterTranslationInterval - 200)
                    collapsedCardTilt = collapsedCardTilt + (card.id to 0f)
                }
            }
        }
        animationPhase = AnimationPhase.ANIMATION_COMPLETE
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            data.educationCards.forEach { card ->
                val isExpanded = expandedCardId == card.id
                val hasAppeared = appearedCardIds.contains(card.id)

                // Slide from bottom
                val offsetY by animateDpAsState(
                    targetValue = if (hasAppeared) 0.dp else 800.dp,
                    animationSpec = tween(durationMillis = data.animations.bottomToCenterTranslationInterval.toInt(), easing = FastOutLinearInEasing)
                )

                // Animate tilt
                val rotationZee by animateFloatAsState(
                    targetValue = collapsedCardTilt[card.id] ?: 0f,
                    animationSpec = tween(durationMillis = data.animations.bottomToCenterTranslationInterval.toInt()/4, easing = LinearEasing)
                )

                EducationCard(
                    card = card,
                    isExpanded = isExpanded,
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = offsetY.toPx()
                            rotationZ = rotationZee
                        }
                        .clickable(enabled = animationPhase == AnimationPhase.ANIMATION_COMPLETE) {
                            expandedCardId = card.id
                            onCardClicked(card.id)
                        }
                )
            }
        }

        //Save Button
        if (animationPhase == AnimationPhase.ANIMATION_COMPLETE) {
            Button(
                onClick = onSaveClicked,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .wrapContentWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = data.saveButton.backgroundColor
                ),
                border = BorderStroke(1.dp, data.saveButton.strokeColor)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = data.saveButton.text,
                        style = MaterialTheme.typography.titleMedium,
                        color = data.saveButton.textColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.Url(data.ctaLottie)
                    )
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier.size(32.dp),
                        iterations = LottieConstants.IterateForever
                    )
                }
            }
        }
    }
}




