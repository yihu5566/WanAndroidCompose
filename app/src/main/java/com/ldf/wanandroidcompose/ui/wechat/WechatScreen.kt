package com.ldf.wanandroidcompose.ui.wechat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.ui.home.ArticleItem
import com.ldf.wanandroidcompose.ui.project.SwipeRefreshList
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.viewmodel.CollectViewModel
import com.ldf.wanandroidcompose.ui.viewmodel.PublicNumViewModel
import com.ldf.wanandroidcompose.ui.widget.SimpleCard

/**
 * @Author : dongfang
 * @Created Time : 2023-06-01  09:27
 * @Description:
 */
@Composable
fun WechatScreen(navHostController: NavHostController) {
    val numViewModel: PublicNumViewModel = viewModel()
    val collectViewModel: CollectViewModel = viewModel()
    val lazyPagingItems =
        numViewModel.wechatArticleListData.collectAsLazyPagingItems()
    val lazyListState = numViewModel.wechatLazyListState
    //TopBar的Index改变
    LaunchedEffect(Nav.publicNumIndex.value) {
        if (Nav.publicNumIndex.value == numViewModel.saveChangeWechatIndex) return@LaunchedEffect
        numViewModel.apply {
            LogUtils.d("刷新拉")

            //保存改变过index和offset的指示器Index
            saveChangeWechatIndex = Nav.publicNumIndex.value
            lazyListState.scrollToItem(0, 0)
            lazyPagingItems.refresh()
        }
    }
    // 列表数据
    SwipeRefreshList(
        lazyListState,
        lazyPagingItems
    ) { _: Int, data: Article ->
        SimpleCard {
            ArticleItem(data, collectViewModel) {
                navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${data.link}")
            }
        }
    }
}