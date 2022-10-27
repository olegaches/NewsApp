package com.newstestproject.presentation.category_search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.newstestproject.R
import com.newstestproject.core.presentation.ui.theme.HomeBackground
import com.newstestproject.presentation.Screen
import com.newstestproject.presentation.category_search.components.CategoryItem
import com.newstestproject.presentation.category_search.components.CategorySearchTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySearchScreen(
    navController: NavController,
    viewModel: CategorySearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    BackHandler(
        enabled = true,
        onBack  = {
            navController.navigate(Screen.CategoriesScreen.route) {
                popUpTo(Screen.CategoriesScreen.route) {
                    inclusive = true
                }
            }
        }
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            CategorySearchTopBar(
                query = state.query,
                containerColor = MaterialTheme.colors.background,
                onSearch = { viewModel.onSearch(it) },
                title = Screen.SearchCategoryScreen.screenName.asString(),
                scrollBehavior = scrollBehavior,
                onTopBarClick = {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                },
                onBackButtonClick = {
                    navController.navigate(Screen.CategoriesScreen.route) {
                        popUpTo(Screen.CategoriesScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        },

        ) {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(false),
            onRefresh = { viewModel.loadCategories() }) {

            Box(Modifier.fillMaxSize()) {

                LazyColumn(state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(
                        count = state.categories.size,
                    ) { index ->
                        CategoryItem(
                            category = state.categories[index],
                            onItemClick = {
                                viewModel.onCategoryClicked(index)
                            }
                        )
                    }
                }
            }
        }
    }
}