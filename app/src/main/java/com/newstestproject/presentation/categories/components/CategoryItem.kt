package com.newstestproject.presentation.categories.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.newstestproject.domain.model.Category

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier,
    onItemClick: () -> Unit,
) {
    Row {
        Text(
            text = category.name,
        )
        IconToggleButton(
            checked = category.selected,
            onCheckedChange = { onItemClick() },
        ) {

        }
    }
}