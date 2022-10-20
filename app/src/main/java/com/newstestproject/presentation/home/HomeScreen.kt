package com.newstestproject.presentation.home

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newstestproject.R
import com.newstestproject.core.presentation.ui.theme.HomeBackground
import com.newstestproject.core.util.UiText
import com.newstestproject.presentation.home.components.NewsItem
import com.newstestproject.util.CategoryName
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.loadErrors.collectLatest { authResult ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = authResult.asString(context),
                duration = SnackbarDuration.Short
            )
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            val filterItems = state.categories
            TopBar(
                query = state.query,
                onSearch = { viewModel.onSearch(it) },
                filterItems = filterItems,
                filterState = state.selectedFilter,
                defaultFilter = CategoryName.general,
                scrollBehavior = scrollBehavior,
                onFilter = { viewModel.onFilter(it) },
                onTopBarClick = {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                },
            )
        },

    ) {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(false),
            onRefresh = { viewModel.loadNews() }) {

            Box(Modifier.fillMaxSize()) {
                if (state.error == null && !state.isLoading && state.data.isEmpty()) {
                    val placeHolder: String = if (state.selectedFilter == CategoryName.general)
                        stringResource(R.string.news_list_placeholder)
                    else
                        stringResource(
                            R.string.news_list_category_placeholder,
                            state.selectedFilter.localizedName.asString(context)
                        )
                    Text(
                        text = placeHolder,
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.Center),
                        softWrap = true,
                        textAlign = TextAlign.Center
                    )
                }

                LazyColumn(state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(HomeBackground),
                ) {
                    items(state.data) { article ->
                        NewsItem(
                            article = article,
                            onItemClick = {
                                openTab(context, article.url)
                            }
                        )
                    }
                }

                if(state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.primaryVariant
                    )
                }
                if(state.data.isEmpty()) {
                    ErrorHandler(error = state.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(20.dp),
                        onRefreshClick = {
                            viewModel.loadNews()
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(query: String,
           scrollBehavior: TopAppBarScrollBehavior,
           filterItems: List<CategoryName>,
           filterState: CategoryName,
           defaultFilter: CategoryName,
           onTopBarClick: () -> Unit,
           onFilter: (CategoryName) -> Unit,
           onSearch: (String) -> Unit,
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colors.primary
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
                        tint = MaterialTheme.colors.onPrimary
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
                        backgroundColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        focusedIndicatorColor = MaterialTheme.colors.secondary,
                        unfocusedIndicatorColor = MaterialTheme.colors.secondary),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = { searchMode = false; onSearch("") }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                        }
                    },
                    trailingIcon = {
                        if(query.isNotEmpty()) {
                            IconButton(onClick = { onSearch("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Icon")
                            }
                        }
                    },
                    keyboardActions = KeyboardActions.Default
                )
            }
        },
        navigationIcon = {
            var expanded : Boolean by remember { mutableStateOf(false) }
            Column {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            expanded = !expanded
                        }
                        .padding(17.dp)
                ) {
                    Text(
                        text = filterState.localizedName.asString(),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "ArrowDropDown icon",
                        modifier = Modifier.align(Alignment.Bottom)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(270.dp)
                    ) {
                    (listOf(defaultFilter).plus(filterItems)).forEach { category ->
                        DropdownMenuItem(
                            content = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = SpaceBetween
                                ) {
                                    Text(
                                        modifier = Modifier.padding(10.dp),
                                        text = category.localizedName.asString(),
                                        color = MaterialTheme.colors.onBackground,
                                        fontSize = 18.sp
                                    )
                                    if(filterState == category) {
                                        Icon(
                                            imageVector = Icons.Rounded.CheckCircle,
                                            contentDescription = "Selected icon",
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            tint = MaterialTheme.colors.primaryVariant
                                        )
                                    }
                                }
                            },
                            onClick = {
                                expanded = false
                                onFilter(category)
                            },
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ErrorHandler(error: UiText?, modifier: Modifier, onRefreshClick: () -> Unit) {
    error?.let { errorMsg ->
        Column(
            modifier = modifier
        ) {
            Text(
                text = errorMsg.asString(),
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            TextButton(
                onClick = onRefreshClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),

                ) {
                Text(
                    text = stringResource(R.string.refresh_page_tip),
                    color = MaterialTheme.colors.primaryVariant)
            }
        }
    }
}

fun openTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)
    builder.setInstantAppsEnabled(true)
    val customBuilder = builder.build()
    customBuilder.launchUrl(context, Uri.parse(url))
}