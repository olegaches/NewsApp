package com.newstestproject.presentation.categories.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newstestproject.domain.model.Category

@Composable
fun CategoryItem(
    category: Category,
    onDeleteIconClick: () -> Unit,
) {
    val shape = RoundedCornerShape(5.dp)
    Card(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp),
        elevation = 1.dp,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp),
        ) {
            Text(
                text = category.name.localizedName.asString(),
                fontSize = 20.sp
            )
            IconButton(
                onClick = onDeleteIconClick,
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(30.dp),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    tint = Color.Red,
                )
            }
        }
    }
}