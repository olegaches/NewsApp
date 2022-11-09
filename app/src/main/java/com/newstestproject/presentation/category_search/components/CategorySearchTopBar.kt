package com.newstestproject.presentation.category_search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newstestproject.core.presentation.ui.theme.SearchBackGround
import com.newstestproject.util.CategoryName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySearchTopBar(
    query: String,
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    containerColor: Color = MaterialTheme.colors.surface,
    onTopBarClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    onSearch: (String) -> Unit,
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.SemiBold
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = containerColor
        ),
        modifier = Modifier
            .shadow(4.dp)
            .clickable {
                onTopBarClick()
            },
        actions = {
            var searchMode: Boolean by remember { mutableStateOf(false) }

            if(!searchMode) {
                IconButton(
                    onClick = { searchMode = !searchMode },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            else {
                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                OutlinedTextField(
                    value = query,
                    onValueChange = onSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .focusRequester(focusRequester),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = SearchBackGround,
                        focusedIndicatorColor = MaterialTheme.colors.background,
                        unfocusedIndicatorColor = MaterialTheme.colors.background),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = { searchMode = false; onSearch("") }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Icon",
                                tint = MaterialTheme.colors.primary,
                            )
                        }
                    },
                    trailingIcon = {
                        if(query.isNotEmpty()) {
                            IconButton(onClick = { onSearch("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear Icon",
                                    tint = MaterialTheme.colors.primary,
                                )
                            }
                        }
                    },
                    keyboardActions = KeyboardActions.Default
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    )
}