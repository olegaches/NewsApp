package com.newstestproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.newstestproject.R
import com.newstestproject.core.presentation.ui.theme.NewsTestProjectTheme
import com.newstestproject.presentation.categories.CategoriesScreen
import com.newstestproject.presentation.components.BottomNavigationBar
import com.newstestproject.presentation.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTestProjectTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = Screen.HomeScreen.name,
                                    route = Screen.HomeScreen.route,
                                    iconId = R.drawable.ic_home
                                ),
                                BottomNavItem(
                                    name = Screen.CategoriesScreen.name,
                                    route = Screen.CategoriesScreen.route,
                                    iconId = R.drawable.ic_star,
                                ),
                            ),
                            backGroundColor = MaterialTheme.colors.background,
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
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
            CategoriesScreen()
        }
    }
}

