package com.newstestproject.presentation.home

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newstestproject.R
import com.newstestproject.core.util.UiText
import com.newstestproject.presentation.Screen
import com.newstestproject.presentation.home.components.NewsItem
import com.newstestproject.util.Constants
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(false),
        onRefresh = { viewModel.loadNews() }) {

        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    if(state.error == null && !state.isLoading && state.data.isEmpty()) {
                        Text(text = stringResource(R.string.news_list_placeholder))
                    }
                }
                items(state.data) { article ->
                    val context = LocalContext.current
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
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            ErrorHandler(error = state.error,
                modifier = Modifier.align(Alignment.Center).padding(20.dp),
                onRefreshClick = {
                    viewModel.loadNews()
                })
        }
    }
}

@Composable
fun ErrorHandler(error: UiText?, modifier: Modifier, onRefreshClick: () -> Unit) {
    error?.let { errorMsg ->
        Column(
            modifier = modifier
        ) {
            Text(
                text = errorMsg.asString(),
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
            )
            TextButton(
                onClick = onRefreshClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),

                ) {
                Text(text = stringResource(R.string.refresh_page_tip))
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