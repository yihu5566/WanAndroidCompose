package com.ldf.wanandroidcompose.ui.square

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.MySystem
import com.ldf.wanandroidcompose.data.bean.Navigation
import com.ldf.wanandroidcompose.ui.home.ArticleItem
import com.ldf.wanandroidcompose.ui.project.ProjectSwipeRefreshList
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.viewmodel.SquareViewModel
import com.ldf.wanandroidcompose.ui.widget.FlowBoxGap
import com.ldf.wanandroidcompose.ui.widget.LabelCustom
import com.ldf.wanandroidcompose.ui.widget.SimpleCard

/**
 * @Author : dongfang
 * @Created Time : 2023-06-01  09:27
 * @Description:
 */
@Composable
fun SquareScreen(navHostController: NavHostController) {
    val squareViewModel: SquareViewModel = viewModel()
    val squareArticlePagingItems = squareViewModel.squareArticleData.collectAsLazyPagingItems()
    val askArticlePagingItems = squareViewModel.askArticleData.collectAsLazyPagingItems()
    val systemTreeData = squareViewModel.systemTreeData.observeAsState()
    val navigationTreeData = squareViewModel.navigationTreeData.observeAsState()

    when (Nav.squareTopBarIndex.value) {
        0 -> {
            ProjectSwipeRefreshList(
                squareViewModel.squareIndexState,
                pagingItems = squareArticlePagingItems
            ) { index: Int, data: Article ->
                SimpleCard {
                    ArticleItem(data) {
                        navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${data.link}")
                    }
                }
            }
        }

        1 -> {
            ProjectSwipeRefreshList(
                squareViewModel.questionIndexState,
                pagingItems = askArticlePagingItems
            ) { index: Int, data: Article ->
                SimpleCard {
                    ArticleItem(data) {
                        navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${data.link}")
                    }
                }
            }
        }

        2 -> {
            if (systemTreeData.value == null) squareViewModel.fetchTreeList()
            SystemCardItemContent(squareViewModel.systemIndexState, systemTreeData)
        }

        else -> {
            if (navigationTreeData.value == null) squareViewModel.fetchNavigationList()
            NavigationCardItemContent(squareViewModel.naviIndexState, navigationTreeData)
        }
    }


}

@Composable
fun NavigationCardItemContent(
    navigationState: LazyListState,
    navigationTreeData: State<List<Navigation>?>
) {
    var count = navigationTreeData.value?.size ?: 0
    var dataList = navigationTreeData.value
    LazyColumn(state = navigationState, content = {
        items(count = count) {
            val childItem = dataList?.get(it)?.articles
            val childName = dataList?.get(it)?.name
            SimpleCard {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 10.dp)
                ) {
                    Text(text = childName ?: "", style = MaterialTheme.typography.h5)
                    //标签
                    LabelCustom(itemGap = FlowBoxGap(6.dp)) {
                        childItem?.forEach { data ->
                            Button(onClick = {}) { Text(data.title) }
                        }
                    }
                }
            }
        }
    })

}

@Composable
fun SystemCardItemContent(systemState: LazyListState, data: State<List<MySystem>?>) {
    var count = data.value?.size ?: 0
    LazyColumn(state = systemState, content = {
        items(count = count) {
            val childItem = data.value?.get(it)?.children
            val childName = data.value?.get(it)?.name
            SimpleCard {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 10.dp)
                ) {
                    Text(text = childName ?: "", style = MaterialTheme.typography.h5)
                    //标签
                    LabelCustom(itemGap = FlowBoxGap(6.dp)) {
                        childItem?.forEach { data ->
                            Button(onClick = {}) { Text(data.name) }
                        }
                    }
                }
            }
        }
    })
}
