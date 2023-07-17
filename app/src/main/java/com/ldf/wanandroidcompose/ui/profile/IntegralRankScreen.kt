package com.ldf.wanandroidcompose.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ldf.wanandroidcompose.data.bean.CoinInfo
import com.ldf.wanandroidcompose.ui.project.ProjectSwipeRefreshList
import com.ldf.wanandroidcompose.ui.viewmodel.IntegralRankListViewModel
import com.ldf.wanandroidcompose.ui.widget.AppBar
import com.ldf.wanandroidcompose.ui.widget.BaseScreen

/**
 * @Author : dongfang
 * @Created Time : 2023-07-14  11:53
 * @Description:
 */
@Composable
fun IntegralRankScreen(navHostController: NavHostController) {

    val viewModel: IntegralRankListViewModel = viewModel()
    val pagingItems = viewModel.integralRankListData.collectAsLazyPagingItems()

    BaseScreen {
        Scaffold(topBar = { AppBar(title = "积分排行") }) {
            ProjectSwipeRefreshList(
                state = viewModel.integralRankLazyListState,
                pagingItems = pagingItems
            ) { _, data ->
                IntegralRankCompose(navHostController, Modifier.padding(it), data)
            }
        }
    }
}

@Composable
fun IntegralRankCompose(navHostController: NavHostController, modifier: Modifier, data: CoinInfo) {
    Column {
        Row(
            modifier = modifier.padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${data.rank}.")
            Text(text = data.username, modifier = Modifier.padding(start = 20.dp))
            Text(text = " 积分：${data.coinCount}")
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = MaterialTheme.colors.primary),
                text = "LV：${data.level}",
                color = MaterialTheme.colors.onSecondary
            )
        }
        Divider()
    }
}
