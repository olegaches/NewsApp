package com.newstestproject.presentation.categories.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newstestproject.domain.model.Category

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .clickable { onItemClick() },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = category.name,
                fontSize = 30.sp
            )
            if(category.selected) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(30.dp),
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    }
}