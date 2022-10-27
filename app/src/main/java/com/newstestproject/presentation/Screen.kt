package com.newstestproject.presentation

import com.newstestproject.R
import com.newstestproject.core.util.UiText

enum class Screen(val route: String, val screenName: UiText, val subRoutes: List<String>? = null) {
    HomeScreen("home_screen", UiText.StringResource(R.string.home_screen_name)),
    CategoriesScreen(
        "categories_screen",
        UiText.StringResource(R.string.categories_screen_name),
        listOf("search_categories_screen")),
    SearchCategoryScreen("search_categories_screen",UiText.StringResource(R.string.search_categories_screen_name))
}