package com.ldf.wanandroidcompose.ui.profile

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.data.bean.CollectArticle
import com.ldf.wanandroidcompose.ui.project.ProjectSwipeRefreshList
import com.ldf.wanandroidcompose.ui.viewmodel.CollectListViewModel
import com.ldf.wanandroidcompose.ui.viewmodel.CollectViewModel
import com.ldf.wanandroidcompose.ui.widget.AppBar
import com.ldf.wanandroidcompose.ui.widget.BaseScreen
import com.ldf.wanandroidcompose.ui.widget.CollectCompose
import com.ldf.wanandroidcompose.ui.widget.SimpleCard

/**
 * @Author : dongfang
 * @Created Time : 2023-07-11  15:00
 * @Description:
 */
@Composable
fun MyCollectScreen(navHostController: NavHostController) {
    val viewModel: CollectListViewModel = viewModel()
    val viewModelCollect: CollectViewModel = viewModel()
    BaseScreen {
        Scaffold(topBar = { AppBar(title = "我的收藏") }) { paddingValues ->
            MyCollectCompose(paddingValues, navHostController, viewModel, viewModelCollect)
        }
    }
}

@Composable
fun MyCollectCompose(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    viewModel: CollectListViewModel,
    collectViewModel: CollectViewModel
) {

    val pagingItems = viewModel.collectListData.collectAsLazyPagingItems()

    ProjectSwipeRefreshList(
        state = viewModel.collectLazyListState,
        pagingItems = pagingItems
    ) { _, data ->
        var collectState by remember { mutableStateOf(data.visible) }
        SimpleCard {
            CollectArticleItem(
                data,
                onClick = {
                    navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${data.link}")
                }, collectState
            ) {
                collectState = if (collectState == 0) {
                    collectViewModel.unCollectArticle(data.id)
                    1
                } else {
                    collectViewModel.collectArticle(data.id)
                    0
                }
            }
        }
    }
}

@Composable
fun CollectArticleItem(
    data: CollectArticle, onClick: () -> Unit = {},
    collectState: Int,
    onCollectClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = MaterialTheme.colors.background)
            .clickable(onClick = onClick)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (data.author.isNotEmpty()) {
                Text(
                    text = data.author,
                    modifier = Modifier
                        .padding(5.dp),
                )
            }

            Spacer(modifier = Modifier.weight(1f, true))
            Text(
                text = data.niceDate,
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.h6.copy(color = Color.Gray, fontSize = 10.sp)
            )
        }
        var textColor = MaterialTheme.colors.onSecondary.toArgb()
        AndroidView(
            factory = { context -> TextView(context) },
            update = {
                it.text = HtmlCompat.fromHtml(data.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
                it.setTextColor(textColor)
            }
        )

        Spacer(modifier = Modifier.height(height = 10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            AndroidView(
                factory = { context -> TextView(context) },
                update = {
                    it.text = HtmlCompat.fromHtml(
                        data.chapterName,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                    it.setTextColor(textColor)
                }
            )
            Spacer(modifier = Modifier.weight(weight = 1f, true))
            CollectCompose(collectState == 0, onCollectClick)
        }
    }
}
