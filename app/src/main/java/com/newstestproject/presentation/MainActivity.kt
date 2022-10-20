package com.newstestproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.newstestproject.R
import com.newstestproject.core.presentation.ui.theme.NewsTestProjectTheme
import com.newstestproject.presentation.categories.CategoriesScreen
import com.newstestproject.presentation.category_search.CategorySearchScreen
import com.newstestproject.presentation.components.BottomNavigationBar
import com.newstestproject.presentation.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTestProjectTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = MaterialTheme.colors.primary)
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    screen = Screen.HomeScreen,
                                    iconId = R.drawable.ic_home
                                ),
                                BottomNavItem(
                                    screen = Screen.CategoriesScreen,
                                    iconId = R.drawable.ic_star,
                                ),
                            ),
                            backgroundColor = MaterialTheme.colors.primary,
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.screen.route) {
                                    popUpTo(it.screen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Navigation(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
        composable(Screen.CategoriesScreen.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.SearchCategoryScreen.route) {
            CategorySearchScreen(navController)
        }
    }
}

