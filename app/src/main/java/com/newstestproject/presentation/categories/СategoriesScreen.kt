package com.newstestproject.presentation.categories

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newstestproject.presentation.categories.components.CategoryItem
import com.newstestproject.presentation.categories.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                query = "",
                scrollBehavior = scrollBehavior,
                onTopBarClick = {},
                onSearch = {},
            )
        }
    ) {
        LazyColumn() {
            items(
                count = state.categories.size,
            ) { index ->
                CategoryItem(
                    category = state.categories[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onItemClick = {
                        viewModel.onCategoryClicked(index)
                    }
                )
            }
        }
    }
}