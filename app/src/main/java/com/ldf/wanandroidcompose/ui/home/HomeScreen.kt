package com.ldf.wanandroidcompose.ui.home

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.guru.composecookbook.carousel.PagerState
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.R
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.ui.project.ProjectSwipeRefreshList
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.ColorPallet
import com.ldf.wanandroidcompose.ui.theme.blue500
import com.ldf.wanandroidcompose.ui.theme.green500
import com.ldf.wanandroidcompose.ui.theme.orange500
import com.ldf.wanandroidcompose.ui.theme.typography
import com.ldf.wanandroidcompose.ui.utils.TestTags
import com.ldf.wanandroidcompose.ui.viewmodel.HomeViewModel
import com.ldf.wanandroidcompose.ui.widget.SimpleCard
import com.ldf.wanandroidcompose.ui.widget.carousel.CarouselDot
import com.ldf.wanandroidcompose.ui.widget.carousel.Pager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * @Author : dongfang
 * @Created Time : 2023-05-31  17:29
 * @Description:
 */


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    appThemeState: MutableState<AppThemeState>,
) {
    val showMenu = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val homeViewModel = HomeViewModel()
    //获取轮播图
    homeViewModel.fetchBanners()
    Scaffold(modifier = Modifier.testTag(TestTags.HOME_SCREEN_ROOT),
        content = { paddingValues ->
            HomeScreenContent(
                modifier = Modifier.padding(paddingValues),
                homeViewModel = homeViewModel,
                navHostController
            )
        })
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController
) {
    var itemList = remember { homeViewModel.bannerListLiveData }
    LogUtils.d("列表数据" + itemList.size)
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val isWiderScreen = screenWidth > 550 // Random number for now
    if (itemList.size == 0) {
        return
    }
    val pagerState: PagerState = run {
        remember { PagerState(1, 0, itemList.size - 1) }
    }
    val selectedPage = remember { mutableStateOf(2) }
    homeViewModel.fetchTopArticleList()
    val articleTopData = homeViewModel.articleTopList.observeAsState()
    //列表数据
    val pagingItems = homeViewModel.homeListData.collectAsLazyPagingItems()
    Box(modifier = modifier) {
        Column {
            PrepareFirstPager(pagerState, itemList, selectedPage)
            ProjectSwipeRefreshList(
                homeViewModel.homeLazyListState,
                pagingItems, {
                    articleTopData.value?.let { it ->
                        items(it) {
                            SimpleCard {
                                ArticleItem(it) {
                                    navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${it.link}")
                                }
                            }
                        }
                    }
                }
            ) { _: Int, data: Article ->
                SimpleCard {
                    ArticleItem(data) {
                        navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=${data.link}")
                    }
                }
            }
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
        modifier = Modifier.height(200.dp)
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

    Spacer(modifier = Modifier.height(24.dp))
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
                .padding(18.dp)
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Text(
            text = item.title,
            style = typography.h6.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.BottomStart),
        )
    }
}