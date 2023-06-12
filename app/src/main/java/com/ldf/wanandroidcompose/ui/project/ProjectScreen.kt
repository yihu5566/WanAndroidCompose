package com.ldf.wanandroidcompose.ui.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.viewmodel.ProjectViewModel

/**
 * @Author : dongfang
 * @Created Time : 2023-06-01  09:27
 * @Description:
 */
@Composable
fun ProjectScreen(navHostController: NavHostController) {
    val projectViewModel: ProjectViewModel = viewModel()
    //项目列表数据
    val projectListData = projectViewModel.projectListData.collectAsLazyPagingItems()
    val context = LocalContext.current

    //TopBar的Index改变
    LaunchedEffect(Nav.projectTopBarIndex.value) {

        if (Nav.projectTopBarIndex.value == projectViewModel.saveChangeProjectIndex) return@LaunchedEffect

        projectViewModel.apply {
            //保存改变过index和offset的指示器Index
            saveChangeProjectIndex = Nav.projectTopBarIndex.value
            projectLazyListState.scrollToItem(0, 0)
        }

        projectListData.refresh()
    }
    // 列表数据
    ProjectSwipeRefreshList(projectViewModel, context)
}