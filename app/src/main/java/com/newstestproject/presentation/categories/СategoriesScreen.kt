package com.newstestproject.presentation.categories

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.newstestproject.presentation.categories.components.CategoryItem

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    LazyColumn() {
        items(state.categories) { category ->
            CategoryItem(
                category = category,
                modifier = Modifier,
                onItemClick = {
                    category.selected = !category.selected
                }
            )
        }
    }
}