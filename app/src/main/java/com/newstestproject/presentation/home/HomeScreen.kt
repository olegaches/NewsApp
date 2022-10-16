package com.newstestproject.presentation.home

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.newstestproject.R
import com.newstestproject.presentation.Screen
import com.newstestproject.presentation.home.components.NewsItem
import com.newstestproject.util.Constants
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(false),
        onRefresh = {
            viewModel.loadNews()
        }) {

        Box(Modifier.fillMaxSize()) {
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                if(state.data.isEmpty()) {
                    Text(text = stringResource(R.string.news_list_placeholder))
                }
            }
            items(state.data) { article ->
                val context = LocalContext.current
                NewsItem(
                    modifier = Modifier,
                    article = article,
                    onItemClick = {
                        openTab(context, article.url)
                    }
                )
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