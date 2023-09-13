package com.ldf.wanandroidcompose.ui.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.blankj.utilcode.util.LogUtils
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ldf.wanandroidcompose.R
import java.util.Timer
import kotlin.concurrent.timerTask

/**
 *
 * @Author : dongfang
 * @Created Time : 2023/6/12  16:55
 * @param itemContent 单独的列表项
 * @Description:
 *
 */
@Composable
fun <T : Any> SwipeRefreshList(
    state: LazyListState = rememberLazyListState(),
    pagingItems: LazyPagingItems<T>,
    itemContent: LazyListScope.() -> Unit = {},
    errorClick: () -> Unit = {},
    content: @Composable (index: Int, data: T) -> Unit
) {
    //刷新状态记录
    val refreshState = rememberSwipeRefreshState(false)
    Box(
        modifier = Modifier
            .padding(bottom = 6.dp, top = 6.dp)
            .padding(start = 8.dp, end = 8.dp)
    ) {
        SwipeRefresh(state = refreshState, onRefresh = {
            refreshState.isRefreshing = true
            pagingItems.refresh()
            LogUtils.d("下拉刷新拉")
            Timer().schedule(timerTask {
                refreshState.isRefreshing = false
            }, 3000)
        }) {
            pagingStateUtil(pagingItems, refreshState, errorClick) {
                LazyColumn(state = state) {
                    itemContent()
                    itemsIndexed(pagingItems) { index, data ->
                        content(index, data!!)
                    }
                }
            }
        }
    }
}

@Composable
fun <T : Any> pagingStateUtil(
    //paging数据
    pagingItems: LazyPagingItems<T>,
    //刷新状态
    refreshState: SwipeRefreshState,
    errorClick: () -> Unit = {},
    content: @Composable () -> Unit
) {

    when (pagingItems.loadState.refresh) { //FIRST LOAD
        //未加载且未观察到错误
        is LoadState.NotLoading -> NotLoading(refreshState) {
            when (pagingItems.itemCount) {
                0 -> {
                    ErrorComposable("暂无数据，请点击重试") {
                        pagingItems.refresh()
                    }
                }

                else -> content()
            }
        }

        is LoadState.Error -> {
            ErrorComposable {
                errorClick()
            }
        }

        is LoadState.Loading -> { // Loading UI
            LoadingCompose()
        }
    }
    when (pagingItems.loadState.append) { // Pagination
        is LoadState.Error -> {
            ErrorComposable {
                errorClick()
            }
        }

        is LoadState.Loading -> { // Pagination Loading UI
            LoadingCompose()
        }


        else -> {}
    }
}

@Composable
private fun NotLoading(
    refreshState: SwipeRefreshState,
    content: @Composable () -> Unit
) {

    content()

    //让刷新头停留一下子再收回去
    Timer().schedule(timerTask {
        refreshState.isRefreshing = false
    }, 3000)
}

@Composable
private fun LoadingCompose() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
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

/**
 * 无数据时候的布局
 */
@Composable
fun ErrorComposable(title: String = "网络不佳，请点击重试", block: () -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(300.dp, 180.dp),
            painter = painterResource(id = R.mipmap.ic_net_empty),
            contentDescription = "网络问题",
            contentScale = ContentScale.Crop
        )

        Button(modifier = Modifier.padding(8.dp), onClick = block) {
            Text(title)
        }
    }
}
