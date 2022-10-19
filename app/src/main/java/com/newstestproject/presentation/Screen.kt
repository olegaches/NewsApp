package com.newstestproject.presentation

sealed class Screen(val route: String, val name: String) {
    object HomeScreen: Screen("home_screen", "Новости")
    object CategoriesScreen: Screen("categories_screen", "Категории")
}