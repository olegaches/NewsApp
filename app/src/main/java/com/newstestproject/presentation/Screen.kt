package com.newstestproject.presentation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object NewsDetailScreen: Screen("news_detail_screen")
}