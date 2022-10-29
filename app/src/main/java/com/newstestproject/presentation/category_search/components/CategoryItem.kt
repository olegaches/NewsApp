package com.newstestproject.presentation.category_search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newstestproject.domain.model.Category

@Composable
fun CategoryItem(
    category: Category,
    onItemClick: () -> Unit,
) {
    val shape = RoundedCornerShape(5.dp)
    Card(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(vertical = 1.dp)
            .clickable { onItemClick() },
        elevation = 1.dp,
    ) {
        Row(
            Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = category.name.localizedName.asString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            if(category.selected) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(25.dp),
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primaryVariant,
                )
            }
        }
    }
}