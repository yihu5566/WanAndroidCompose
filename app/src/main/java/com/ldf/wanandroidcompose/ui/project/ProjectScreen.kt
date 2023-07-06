package com.ldf.wanandroidcompose.ui.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.viewmodel.CollectViewModel
import com.ldf.wanandroidcompose.ui.viewmodel.ProjectViewModel
import com.ldf.wanandroidcompose.ui.widget.SimpleCard

/**
 * @Author : dongfang
 * @Created Time : 2023-06-01  09:27
 * @Description:
 */
@Composable
fun ProjectScreen(navHostController: NavHostController) {
    val projectViewModel: ProjectViewModel = viewModel()
    val collectViewModel: CollectViewModel = viewModel()
    LogUtils.d("初始化项目界面————————》")
    //项目列表数据
    val projectListData = projectViewModel.projectListData.collectAsLazyPagingItems()
    //TopBar的Index改变
    LaunchedEffect(Nav.projectTopBarIndex.value) {
        if (Nav.projectTopBarIndex.value == projectViewModel.saveChangeProjectIndex)
            return@LaunchedEffect
        projectViewModel.apply {
            LogUtils.d("刷新拉")

            //保存改变过index和offset的指示器Index
            saveChangeProjectIndex = Nav.projectTopBarIndex.value
            projectLazyListState.scrollToItem(0, 0)
            projectListData.refresh()
        }
    }
    // 列表数据
    ProjectSwipeRefreshList(
        projectViewModel.projectLazyListState,
        projectListData
    ) { index: Int, data: Article ->
        var collectState by remember { mutableStateOf(data.collect) }

        SimpleCard {
            projectItemWidget(data, isCollect = collectState, onCollectClick = {
                if (collectState) {
                    collectViewModel.unCollectArticle(data.id)
                } else {
                    collectViewModel.collectArticle(data.id)
                }
                collectState = !collectState
            }) {
                navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${data.link}")
            }
        }
    }
}