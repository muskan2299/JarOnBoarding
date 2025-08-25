package com.example.jaronboarding.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jaronboarding.presentation.onboarding.components.AnimatedCardStack

@Composable
fun OnboardingScreen(
    onNavigateToLanding: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.shouldNavigateToLanding) {
        if (uiState.shouldNavigateToLanding) {
            onNavigateToLanding()
            viewModel.onNavigationEventHandled()
        }
    }

    var targetStartColor = Color(0xFF1A1A2E)
    var targetEndColor = Color(0xFF16213E)

    if(uiState.expandedCardId!=null) {
        targetStartColor = uiState.instantSavingData
            ?.educationCards
            ?.get(uiState.expandedCardId!!)
            ?.gradientStart ?: Color(0xFF1A1A2E)

        targetEndColor = uiState.instantSavingData
            ?.educationCards
            ?.get(uiState.expandedCardId!!)
            ?.gradientEnd ?: Color(0xFF16213E)
    }

    // Animate colors separately
    val animatedStart by animateColorAsState(
        targetValue = targetStartColor,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        ),
        label = "GradientStart"
    )

    val animatedEnd by animateColorAsState(
        targetValue = targetEndColor,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        ),
        label = "GradientEnd"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(animatedStart, animatedEnd),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
    ) {
        uiState.instantSavingData?.let { data ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Bar
                OnboardingTopBar(
                    toolBarText = data.toolBarText,
                    toolBarIcon = data.toolBarIcon
                )

                Spacer(modifier = Modifier.height(12.dp))

                AnimatedVisibility(
                    visible = uiState.startCardAnimation,
                    enter = fadeIn(animationSpec = tween(800)) +
                            slideInVertically(
                                initialOffsetY = { -it },
                                animationSpec = tween(800)
                            )
                ) {
                    OnboardingIntroSection(
                        introTitle = data.introTitle,
                        introSubtitle = data.introSubtitle,
                        introSubtitleIcon = data.introSubtitleIcon
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                AnimatedCardStack(
                    data = data,
                    uiState = uiState,
                    onCardClicked = { cardId ->
                        viewModel.onCardClicked(cardId)
                    },
                    onSaveClicked = {
                        viewModel.onSaveButtonClicked()
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Loading State
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 3.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading instant savings...",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Error State
        uiState.error?.let { error ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ErrorContent(
                    error = error,
                    onRetryClick = { viewModel.retryLoading() }
                )
            }
        }
    }
}

@Composable
private fun OnboardingTopBar(
    toolBarText: String,
    toolBarIcon: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(toolBarIcon)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = toolBarText,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun OnboardingIntroSection(
    introTitle: String,
    introSubtitle: String,
    introSubtitleIcon: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Text(
            text = introTitle,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = introSubtitle,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(introSubtitleIcon)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetryClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRetryClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6C63FF)
                )
            ) {
                Text(
                    text = "Try Again",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
