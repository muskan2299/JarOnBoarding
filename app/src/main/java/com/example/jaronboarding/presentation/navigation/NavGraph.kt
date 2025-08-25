package com.example.jaronboarding.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jaronboarding.presentation.landing.LandingScreen
import com.example.jaronboarding.presentation.onboarding.OnboardingScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding",
        modifier = modifier
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onNavigateToLanding = {
                    navController.navigate("landing")
                }
            )
        }

        composable("landing") {
            LandingScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}

