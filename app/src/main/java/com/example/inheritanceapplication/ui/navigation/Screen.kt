package com.example.inheritanceapplication.ui.navigation

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object InheritorsScreen: Screen("inheritors_screen")
}
