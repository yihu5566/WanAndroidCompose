package com.ldf.wanandroidcompose.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.guru.composecookbook.carousel.PagerState
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.ui.project.ProjectSwipeRefreshList
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.typography
import com.ldf.wanandroidcompose.utils.TestTags
import com.ldf.wanandroidcompose.ui.viewmodel.CollectViewModel
import com.ldf.wanandroidcompose.ui.viewmodel.HomeViewModel
import com.ldf.wanandroidcompose.ui.widget.SimpleCard
import com.ldf.wanandroidcompose.ui.widget.carousel.CarouselDot
import com.ldf.wanandroidcompose.ui.widget.carousel.Pager


/**
 * @Author : dongfang
 * @Created Time : 2023-05-31  17:29
 * @Description:
 */


@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    val homeViewModel: HomeViewModel = viewModel()
    val collectViewModel: CollectViewModel = viewModel()
    //获取轮播图
    homeViewModel.fetchBanners()
    Scaffold(modifier = Modifier.testTag(TestTags.HOME_SCREEN_ROOT),
        content = { paddingValues ->
            HomeScreenContent(
                modifier = Modifier.padding(paddingValues),
                homeViewModel = homeViewModel,
                navHostController,
                collectViewModel
            )
        })
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    collectViewModel: CollectViewModel
) {
    var itemList = homeViewModel.bannerListLiveData.observeAsState()
    LogUtils.d("banner列表数据" + itemList.value?.size)
    val screenWidth = LocalConfiguration.current.screenWidthDp
    if (itemList.value?.size == 0) {
        return
    }
    val pagerState: PagerState = run {
        remember { PagerState(1, 0, itemList.value?.size!! - 1) }
    }
    val selectedPage = remember { mutableStateOf(2) }
    homeViewModel.fetchTopArticleList()
    val articleTopData = homeViewModel.articleTopList.observeAsState()
    //列表数据
    val pagingItems = homeViewModel.homeListData.collectAsLazyPagingItems()
    Surface(modifier = modifier) {
        Column {
            PrepareFirstPager(pagerState, itemList.value!!, selectedPage)
            ProjectSwipeRefreshList(
                homeViewModel.homeLazyListState,
                pagingItems, {
                    articleTopData.value?.let { it ->
                        items(it) {
                            HotArticleItem(it, navHostController, collectViewModel)
                        }
                    }
                }
            ) { _: Int, data: Article ->
                HotArticleItem(data, navHostController, collectViewModel)
            }
        }
    }
}

@Composable
fun HotArticleItem(
    it: Article,
    navHostController: NavHostController,
    collectViewModel: CollectViewModel
) {
    var collectState by remember { mutableStateOf(it.collect) }

    SimpleCard {
        ArticleItem(it, isCollect = collectState, onClick = {
            navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${it.link}")
        }) {
            if (collectState) {
                collectViewModel.unCollectArticle(it.id)
            } else {
                collectViewModel.collectArticle(it.id)
            }
            collectState = !collectState
        }
    }
}

@Composable
fun PrepareFirstPager(
    pagerState: PagerState,
    items: List<Banner>,
    selectedPage: MutableState<Int>
) {
    Pager(
        state = pagerState,
        modifier = Modifier.height(180.dp)
    ) {
        val item = items[commingPage]
        selectedPage.value = pagerState.currentPage
        CarouselItem(item)
    }

    Row {
        items.forEachIndexed { index, _ ->
            CarouselDot(
                selected = index == selectedPage.value,
                MaterialTheme.colors.primary,
                Icons.Filled.Lens
            )
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CarouselItem(item: Banner) {
    Box {
        GlideImage(
            model = item.imagePath,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Text(
            text = item.title,
            style = typography.h6.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .align(Alignment.BottomStart),
        )
    }
}