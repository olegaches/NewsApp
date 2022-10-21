package com.newstestproject.presentation.categories

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.newstestproject.R
import com.newstestproject.presentation.Screen
import com.newstestproject.presentation.categories.components.CategoryItem
import com.newstestproject.presentation.categories.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavHostController,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    BackHandler(
        enabled = true,
        onBack  = {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.HomeScreen.route) {
                    inclusive = true
                }
            }
        }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                query = "",
                scrollBehavior = scrollBehavior,
                onTopBarClick = {},
                onSearch = {},
                containerColor = androidx.compose.material.MaterialTheme.colors.background
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if(state.categories.isEmpty()) {
                Text(
                    text = stringResource(R.string.user_categories_placeholder),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            LazyColumn() {
                items(
                    count = state.categories.size,
                ) { index ->
                    CategoryItem(
                        category = state.categories[index],
                        onDeleteIconClick = {
                            viewModel.onDeleteIconClick(index)
                        }
                    )
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.BottomEnd),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 3.dp),
                onClick = {
                    navController.navigate(Screen.SearchCategoryScreen.route)
                },
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add button",
                    tint = androidx.compose.material.MaterialTheme.colors.primary
                )
            }
        }
    }
}