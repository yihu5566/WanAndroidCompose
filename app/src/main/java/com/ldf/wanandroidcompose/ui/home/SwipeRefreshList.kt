package com.ldf.wanandroidcompose.ui.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blankj.utilcode.util.LogUtils
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ldf.wanandroidcompose.ui.utils.TestTags

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
) {
    homeViewModel.fetchTopArticleList()
//    val articleTopData = homeViewModel.articleTopList.observeAsState()
    //列表数据
    val pagingItems = homeViewModel.homeListData.collectAsLazyPagingItems()
    //刷新状态记录
    val swipeableState = rememberSwipeRefreshState(false)
    Box(modifier = Modifier) {
        SwipeRefresh(state = swipeableState, onRefresh = {
            swipeableState.isRefreshing = true
            pagingItems.refresh()
            LogUtils.d("下拉刷新拉")
        }) {
            LazyColumn(
                modifier = Modifier.testTag(TestTags.HOME_SCREEN_LIST),
                state = homeViewModel.homeLazyListState
            ) {

                items(
                    items = pagingItems,
                    key = { it.id }
                ) { it ->
                    ArticleItem(it!!, context, isDarkTheme, isWiderScreen)
                }
                when (val state = pagingItems.loadState.refresh) { //FIRST LOAD
                    is LoadState.Error -> {
                        //TODO Error Item
                        //state.error to get error message
                    }

                    is LoadState.Loading -> { // Loading UI
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "Refresh Loading"
                                )

                                CircularProgressIndicator(color = Color.Black)
                            }
                        }
                    }

                    else -> {
                    }
                }

                when (val state = pagingItems.loadState.append) { // Pagination
                    is LoadState.Error -> {
                        //TODO Pagination Error Item
                        //state.error to get error message
                    }

                    is LoadState.Loading -> { // Pagination Loading UI
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(text = "Pagination Loading")

                                CircularProgressIndicator(color = Color.Black)
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}


@Composable
fun ScrollToTopButton(onClick: () -> Unit, modifier: Modifier) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "Scroll to top",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}