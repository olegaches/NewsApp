package com.newstestproject.presentation

sealed class Screen(val route: String, val name: String, val subRoutes: List<String>? = null) {
    object HomeScreen: Screen("home_screen", "Новости")
    object CategoriesScreen: Screen(
        "categories_screen",
        "Категории",
        listOf("search_categories_screen"))
    object SearchCategoryScreen: Screen("search_categories_screen", "Поиск категорий")
}