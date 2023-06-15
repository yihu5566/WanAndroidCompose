package com.ldf.wanandroidcompose.ui.project

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.blankj.utilcode.util.LogUtils
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ldf.wanandroidcompose.ui.utils.TestTags
import com.ldf.wanandroidcompose.ui.viewmodel.ProjectViewModel
import java.util.Timer
import kotlin.concurrent.timerTask

/**
 *
 * @Author : dongfang
 * @Created Time : 2023/6/12  16:55
 * @Description:
 *
 */
@Composable
fun ProjectSwipeRefreshList(
    viewModel: ProjectViewModel,
    context: Context,
    onClick: () -> Unit
) {

    //列表数据
    val pagingItems = viewModel.projectListData.collectAsLazyPagingItems()
    //刷新状态记录
    val swipeableState = rememberSwipeRefreshState(false)
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(bottom = 6.dp, top = 6.dp)
            .padding(start = 8.dp, end = 8.dp)
    ) {
        SwipeRefresh(state = swipeableState, onRefresh = {
            swipeableState.isRefreshing = true
            pagingItems.refresh()
            LogUtils.d("下拉刷新拉")
            Timer().schedule(timerTask {
                swipeableState.isRefreshing = false
            }, 3000)
        }) {
            LazyColumn(
                modifier = Modifier.testTag(TestTags.HOME_SCREEN_LIST),
                state = viewModel.projectLazyListState
            ) {

                items(
                    items = pagingItems,
                    key = { it.id }
                ) { it ->
                    it?.let {
                        projectItemWidget(it, context)
                    }
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