package com.example.inheritanceapplication.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.inheritanceapplication.MainUiModel
import com.example.inheritanceapplication.ui.details.InheritorsScreen
import com.google.accompanist.navigation.animation.composable
import com.example.inheritanceapplication.ui.main.MainScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    uiModel: MainUiModel
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(
            route = Screen.MainScreen.route,
        ) {
            MainScreen(
                mainUiModel = uiModel,
                navController = navController
            )
        }

        composable(
            route = Screen.InheritorsScreen.route,
        ) {
            InheritorsScreen(
                mainUiModel = uiModel,
                navController = navController
            )
        }
    }
}