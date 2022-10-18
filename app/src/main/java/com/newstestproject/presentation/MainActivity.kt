package com.newstestproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.newstestproject.core.presentation.ui.theme.NewsTestProjectTheme
import com.newstestproject.presentation.Screen
import com.newstestproject.presentation.categories.CategoriesScreen
import com.newstestproject.presentation.components.BottomNavigationBar
import com.newstestproject.presentation.home.HomeScreen
import com.newstestproject.util.Constants
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
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = Screen.CategoriesScreen.name,
                                    route = Screen.CategoriesScreen.route,
                                    icon = Icons.Default.Notifications,
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
        composable(Screen.CategoriesScreen.route) {
            CategoriesScreen()
        }
    }
}
