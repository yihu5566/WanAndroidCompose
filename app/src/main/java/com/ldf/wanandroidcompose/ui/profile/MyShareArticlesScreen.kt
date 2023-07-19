package com.ldf.wanandroidcompose.ui.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.ui.home.ArticleItem
import com.ldf.wanandroidcompose.ui.project.SwipeRefreshList
import com.ldf.wanandroidcompose.ui.viewmodel.CollectListViewModel
import com.ldf.wanandroidcompose.ui.viewmodel.CollectViewModel
import com.ldf.wanandroidcompose.ui.viewmodel.ShareArticlesListViewModel
import com.ldf.wanandroidcompose.ui.widget.AppBar
import com.ldf.wanandroidcompose.ui.widget.BaseScreen
import com.ldf.wanandroidcompose.ui.widget.SimpleCard

/**
 * @Author : dongfang
 * @Created Time : 2023-07-11  15:00
 * @Description:
 */
@Composable
fun MyShareArticlesScreen(navHostController: NavHostController) {
    val viewModel: ShareArticlesListViewModel = viewModel()
    BaseScreen {
        Scaffold(topBar = { AppBar(title = "我的收藏") }) { paddingValues ->
            MyShareArticlesCompose(paddingValues, navHostController, viewModel)
        }
    }
}

@Composable
fun MyShareArticlesCompose(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    viewModel: ShareArticlesListViewModel,
) {
    val pagingItems = viewModel.shareListData.collectAsLazyPagingItems()
    SwipeRefreshList(
        state = viewModel.shareLazyListState,
        pagingItems = pagingItems
    ) { _, data ->
        SimpleCard {
            ArticleItem(data) {
                navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${data.link}")
            }
        }
    }
}


