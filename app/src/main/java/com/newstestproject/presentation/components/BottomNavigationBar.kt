package com.newstestproject.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.newstestproject.presentation.BottomNavItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    containerColor: Color,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(containerColor)
                .shadow(4.dp)
        )
        NavigationBar(
            modifier = modifier
                .height(48.dp)
                .shadow(
                    elevation = 9.dp,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    shape = RoundedCornerShape(0.dp),
                    clip = true
                ),
            containerColor = containerColor,
            tonalElevation = 0.dp,
        ) {
            items.forEach { item ->
                val selected = item.screen.route == backStackEntry.value?.destination?.route
                        || item.screen.subRoutes?.contains(backStackEntry.value?.destination?.route) ?: false
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        onItemClick(item)
                    },
                    enabled = true,
                    colors =  NavigationBarItemDefaults.colors(
                        indicatorColor = containerColor,
                    ),
                    icon = {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                tint = if(selected) MaterialTheme.colors.primaryVariant else Color.Gray,
                                contentDescription = item.screen.screenName.asString()
                            )
                            Text(
                                text = item.screen.screenName.asString(),
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                color = if(selected) MaterialTheme.colors.primaryVariant else Color.Gray,
                            )
                        }
                    }
                )
            }
        }
    }
}