package com.ldf.wanandroidcompose.ui.home

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.blankj.utilcode.util.LogUtils
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ldf.wanandroidcompose.ui.utils.TestTags
import kotlinx.coroutines.launch

/**
 * @Author : dongfang
 * @Created Time : 2023-06-06  13:50
 * @Description:
 */
@Composable
fun SwipeRefreshList(
    homeViewModel: HomeViewModel,
    context: Context,
    isDarkTheme: Boolean,
    isWiderScreen: Boolean,
    progress: Float,
) {
    var itemArticleList = remember { homeViewModel.articleList }
    var list = itemArticleList.value.datas
    val swipeableState = rememberSwipeRefreshState(false)
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    homeViewModel.articlePageListLiveData.observe(LocalLifecycleOwner.current) {
        itemArticleList.value = it
        swipeableState.isRefreshing = false
    }

    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    Box(modifier = Modifier) {
        SwipeRefresh(state = swipeableState, onRefresh = {
            swipeableState.isRefreshing = true
            LogUtils.d("下拉刷新拉")
            homeViewModel.fetchArticlePageList()
        }) {
            LazyColumn(
                modifier = Modifier.testTag(TestTags.HOME_SCREEN_LIST)
            ) {
                items(
                    items = list,
                    itemContent = {
                        ArticleItem(it, context, isDarkTheme, isWiderScreen)
                    }
                )
            }
            val showNoData = (list.isNullOrEmpty() && progress > .97)
            AnimatedVisibility(
                visible = showNoData,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "No results", color = MaterialTheme.colorScheme.onBackground)
            }
            val showLoadingResults = (list.isNullOrEmpty() && progress < .97)
            AnimatedVisibility(
                visible = showLoadingResults,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "Loading results", color = MaterialTheme.colorScheme.onBackground)
            }
            val showButton = listState.firstVisibleItemIndex > 0
            AnimatedVisibility(
                visible = showButton,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                ScrollToTopButton(
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }

            val showProgress = progress < .97
            AnimatedVisibility(visible = showProgress) {
                LinearProgressIndicator(
                    progress = animatedProgress,
                    color = androidx.compose.material.MaterialTheme.colors.secondary,
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
fun ScrollToTopButton(onClick: () -> Unit, modifier: Modifier) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = androidx.compose.material.MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "Scroll to top",
            tint = androidx.compose.material.MaterialTheme.colors.onPrimary
        )
    }
}