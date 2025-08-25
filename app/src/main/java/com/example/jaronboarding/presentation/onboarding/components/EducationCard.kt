package com.example.jaronboarding.presentation.onboarding.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.jaronboarding.domain.model.AnimationConfig
import com.example.jaronboarding.domain.model.EducationCard
import com.example.jaronboarding.domain.model.InstantSavingData


@Composable
fun EducationCard(
    modifier: Modifier = Modifier,
    card: EducationCard,
    isExpanded: Boolean
) {

    val target = if (isExpanded) 1f else 0f
    val progress = animateFloatAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing),
        label = "CardProgress"
    ).value

    // Derive everything from progress
    val cardHeight = lerpDp(68.dp, 440.dp, progress)

    val imageWidthFraction = lerp(0.13f, 0.90f, progress)
    val imageHeight = lerpDp(36.dp, 340.dp, progress)
    val imageAlignment = lerpAlignment(
        start = BiasAlignment(-1f, 0f),   // CenterStart
        end   = BiasAlignment(0f, -1f),   // TopCenter
        t = progress
    )

    val textSizeSp = lerp(14f, 20f, progress)
    val textStartPadding = lerpDp(72.dp, 0.dp, progress)
    val textTopExtra = lerpDp(0.dp, 32.dp, progress) // extra gap under image
    val textAlignment = lerpAlignment(
        start = BiasAlignment(-1f, 0f),   // beside image
        end   = BiasAlignment(0f, -1f),   // below image top-center
        t = progress
    )
    val arrowAlpha = 1f - progress

    // Apply lerp to the commented padding values
    val imageTopPadding = lerpDp(0.dp, 16.dp, progress)
    val imageStartPadding = lerpDp(16.dp, 0.dp, progress)

//    val transition = updateTransition(
//        targetState = isExpanded,
//        label = "CardTransition"
//    )
//
//    // Use same duration + easing everywhere for smoother effect
//    val duration = 800
//    val easing = LinearOutSlowInEasing
//
//    val imageHeight by transition.animateDp(
//        transitionSpec = { tween(durationMillis = duration, easing = easing) },
//        label = "ImageHeight"
//    ) { expanded ->
//        if (expanded) 340.dp else 36.dp
//    }
//
//    val imageWidthFraction by transition.animateFloat(
//        transitionSpec = { tween(durationMillis = duration, easing = easing) },
//        label = "ImageWidthFraction"
//    ) { expanded ->
//        if (expanded) 0.9f else 0.13f
//    }
//
//    val cardHeight by transition.animateDp(
//        transitionSpec = { tween(durationMillis = duration, easing = easing) },
//        label = "CardHeight"
//    ) { expanded ->
//        if (expanded) 440.dp else 68.dp
//    }
//
//    val imageTopPadding by transition.animateDp(
//        transitionSpec = { tween(durationMillis = duration, easing = easing) },
//        label = "ImageTopPadding"
//    ) { expanded ->
//        if (expanded) 16.dp else 0.dp
//    }
//
//    val imageStartPadding by transition.animateDp(
//        transitionSpec = { tween(durationMillis = duration, easing = easing) },
//        label = "ImageStartPadding"
//    ) { expanded ->
//        if (expanded) 0.dp else 16.dp
//    }
//
//    val textSize by transition.animateFloat(
//        transitionSpec = { tween(durationMillis = duration, easing = easing) },
//        label = "TextSize"
//    ) { expanded ->
//        if (expanded) 20f else 14f
//    }
//
//    val textTopPadding by transition.animateDp(
//        transitionSpec = { tween(durationMillis = duration, easing = easing) },
//        label = "TextTopPadding"
//    ) { expanded ->
//        if (expanded) 32.dp else 0.dp
//    }
//
//    val textStartPadding by transition.animateDp(
//        transitionSpec = { tween(durationMillis = duration, easing = easing) },
//        label = "TextStartPadding"
//    ) { expanded ->
//        if (expanded) 0.dp else 72.dp
//    }


    Card(
        modifier = modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(0.9f)
            .height(cardHeight),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(card.backgroundColor.copy(alpha = .8f))
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(card.strokeStartColor, card.strokeEndColor)
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
        ) {
            // Image (same element, morphs in size/position)
            AsyncImage(
                model = card.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = imageTopPadding, start = imageStartPadding)
                    .fillMaxWidth(imageWidthFraction)
                    .height(imageHeight)
                    .clip(RoundedCornerShape(20.dp))
                    .align(imageAlignment),
                contentScale = ContentScale.Crop
            )

            // Text (slides below in expanded, beside in collapsed)
            Text(
                text = if (isExpanded) card.expandedText else card.collapsedText,
                fontSize = textSizeSp.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = if (isExpanded) TextAlign.Center else TextAlign.Start,
                modifier = Modifier
                    .padding(
                        top = textTopExtra + if (isExpanded) imageHeight else 0.dp,
                        start = textStartPadding
                    )
                    .align(textAlignment)
                    .fillMaxWidth(if (isExpanded) 0.9f else 0.7f)
            )

            // Dropdown arrow only in collapsed mode
            if (!isExpanded) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = arrowAlpha),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                )
            }
        }
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float) =
    start + (stop - start) * fraction

private fun lerpDp(start: Dp, stop: Dp, fraction: Float): Dp =
    Dp(lerp(start.value, stop.value, fraction))

private fun lerpAlignment(
    start: BiasAlignment, end: BiasAlignment, t: Float
): BiasAlignment {
    val h = lerp(start.horizontalBias, end.horizontalBias, t)
    val v = lerp(start.verticalBias, end.verticalBias, t)
    return BiasAlignment(h, v)
}


//@Composable
//fun EducationCard(
//    modifier: Modifier,
//    card: EducationCard,
//    isExpanded: Boolean
//) {
//    val cardHeight by animateDpAsState(
//        targetValue = if (isExpanded) 440.dp else 68.dp,
//        animationSpec = tween(500)
//    )
//
//    Card(
//        modifier = modifier
//            .padding(vertical = 12.dp)
//            .fillMaxWidth(0.9f)
//            .height(cardHeight),
//        shape = RoundedCornerShape(28.dp),
//        elevation = CardDefaults.cardElevation(6.dp)
//    ) {
//        if (isExpanded) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(
//                        card.backgroundColor.copy(.8f)
//                    )
//                    .border(
//                        width = 1.dp,
//                        brush = Brush.linearGradient(
//                            colors = listOf(
//                                card.strokeStartColor,
//                                card.strokeEndColor
//                            )
//                        ),
//                        shape = RoundedCornerShape(28.dp)
//                    )
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                AsyncImage(
//                    model = card.imageUrl,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(340.dp)
//                        .clip(RoundedCornerShape(28.dp)),
//                    contentScale = ContentScale.Crop
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    modifier = Modifier.padding(horizontal = 32.dp),
//                    text = card.expandedText.trim(),
//                    style = MaterialTheme.typography.titleMedium,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                )
//            }
//        } else {
//            Row(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(card.backgroundColor.copy(.8f))
//                    .border(
//                        width = 1.dp,
//                        brush = Brush.linearGradient(
//                            colors = listOf(
//                                card.strokeStartColor,
//                                card.strokeEndColor
//                            )
//                        ),
//                        shape = RoundedCornerShape(28.dp)
//                    )
//                    .padding(12.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                AsyncImage(
//                    model = card.imageUrl,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(32.dp, 36.dp)
//                        .clip(RoundedCornerShape(28.dp)),
//                    contentScale = ContentScale.Crop
//                )
//                Text(
//                    text = card.collapsedText,
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = Color.White,
//                    modifier = Modifier.padding(start = 16.dp),
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold
//                )
//                Icon(
//                    imageVector = Icons.Default.KeyboardArrowDown,
//                    contentDescription = null,
//                    tint = Color.White
//                )
//            }
//        }
//    }
//}




