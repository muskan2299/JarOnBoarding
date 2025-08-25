package com.example.jaronboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.jaronboarding.presentation.navigation.NavGraph
import com.example.jaronboarding.presentation.theme.InstantSavingTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen before super.onCreate()
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Optional: Keep splash screen until data is loaded
        splashScreen.setKeepOnScreenCondition {
            // Return true to keep splash screen visible
            // You can add logic here to wait for initial data loading
            false
        }

        setContent {
            InstantSavingTheme {
                SystemUiConfiguration()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InstantSavingApp()
                }
            }
        }
    }
}

@Composable
private fun SystemUiConfiguration() {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        // Set status bar color to transparent for edge-to-edge
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false // Use light icons for dark background
        )

        // Set navigation bar color
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        // Enable immersive mode
        systemUiController.isSystemBarsVisible = true
    }
}

@Composable
private fun InstantSavingApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    JarOnBoardingTheme {
//        Greeting("Android")
//    }
//}