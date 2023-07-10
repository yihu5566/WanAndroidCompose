package com.ldf.wanandroidcompose.ui.search

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.blankj.utilcode.util.ToastUtils
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.HotSearch
import com.ldf.wanandroidcompose.ui.home.HotArticleItem
import com.ldf.wanandroidcompose.ui.project.ProjectSwipeRefreshList
import com.ldf.wanandroidcompose.ui.viewmodel.CollectViewModel
import com.ldf.wanandroidcompose.ui.viewmodel.SearchViewModel
import com.ldf.wanandroidcompose.ui.widget.AppBar
import com.ldf.wanandroidcompose.ui.widget.BaseScreen
import com.ldf.wanandroidcompose.ui.widget.FlowBoxGap
import com.ldf.wanandroidcompose.ui.widget.LabelCustom
import com.ldf.wanandroidcompose.utils.room.HistorySearchKey
import com.ldf.wanandroidcompose.utils.room.SearchHistoryHelper
import com.ldf.wanandroidcompose.utils.room.SearchHistoryHelper.delete
import com.ldf.wanandroidcompose.utils.room.SearchHistoryHelper.insertSearchHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author : dongfang
 * @Created Time : 2023-06-12  14:41
 * @Description: 搜索页面
 */
@Composable
fun SearchScreen(navHostController: NavHostController) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val searchViewModel: SearchViewModel = viewModel()
    val collectViewModel: CollectViewModel = viewModel()
    searchViewModel.getHotSearchList()

    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val listState = searchViewModel.hostSearchTreeData.observeAsState()
    val searchKeyState = searchViewModel.searchKey.observeAsState()


    BaseScreen {
        Scaffold(topBar = {
            AppBar(textFieldValue = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                leftIcon = Icons.Default.ArrowBack, onLeftClick = {
                    navHostController.navigateUp()
                },
                rightIcon = Icons.Default.Search, onRightClick = {
                    if (textFieldValue.text.isBlank()) {
                        searchViewModel.searchKey.postValue(null)
                        return@AppBar
                    }
                    searchViewModel.searchKey.postValue(textFieldValue.text)

                    //保存到room数据库
                    scope.launch {
                        this.insertSearchHistory(context, HistorySearchKey(textFieldValue.text))
                    }
                })
        }) {
            if (searchKeyState.value == null) {
                SearchCompose(context, it, listState, scope)
            } else {
                SearchComplete(searchViewModel, collectViewModel, navHostController)
            }
        }
    }
}

@Composable
fun SearchComplete(
    searchViewModel: SearchViewModel,
    collectViewModel: CollectViewModel,
    navHostController: NavHostController
) {
    val pagingItems = searchViewModel.keySearchData.collectAsLazyPagingItems()

    ProjectSwipeRefreshList(
        searchViewModel.searchLazyListState,
        pagingItems
    ) { _: Int, data: Article ->
        HotArticleItem(data, navHostController, collectViewModel)
    }
}

@Composable
fun SearchCompose(
    context: Context,
    paddingValues: PaddingValues,
    listState: State<List<HotSearch>?>,
    scope: CoroutineScope,
) {
//搜索历史数据
    val searchHistoryListData =
        SearchHistoryHelper.getSearchHistoryAllForFlow(context).collectAsState(initial = listOf())
    Column(modifier = Modifier.padding(paddingValues)) {
        Text(
            text = "热门搜索",
            modifier = Modifier.padding(6.dp),
            fontSize = 16.sp,
            color = MaterialTheme.colors.primary
        )
        if (listState.value?.size != 0) {
            LabelCustom(itemGap = FlowBoxGap(4.dp)) {
                listState.value?.forEach { data ->
                    Button(onClick = {}) { Text(data.name) }
                }
            }
        } else {
            Text(text = "暂无内容")
        }
        Text(
            text = "历史搜索",
            modifier = Modifier.padding(6.dp),
            fontSize = 16.sp,
            color = MaterialTheme.colors.primary
        )
        if (searchHistoryListData.value.isNotEmpty()) {
            LazyColumn {
                items(searchHistoryListData.value) {

                    DeleteHistoryKetItem(modifier = Modifier.clickable {

                    }, it) {
                        scope.launch {
                            this.delete(context, it.id)
                        }
                    }
                }
            }
        } else {
            Text(text = "暂无内容")
        }
    }

}

@Composable
private fun DeleteHistoryKetItem(
    modifier: Modifier,
    it: HistorySearchKey,
    //Icon图标
    imageVector: ImageVector = Icons.Default.Close,
    //图标点击事件
    imageClick: (CoroutineScope.() -> Unit)? = null
) {
    var index by remember { mutableStateOf(0) }

    //获取协程域
    LaunchedEffect(index) {
        if (index == 0) return@LaunchedEffect
        launch(Dispatchers.IO) {
            if (imageClick != null) imageClick()
        }
    }
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = it.title, fontSize = 16.sp)
        Icon(imageVector = imageVector, contentDescription = null, modifier = Modifier.clickable {
            imageClick?.let { index++ }
        })
    }
}


