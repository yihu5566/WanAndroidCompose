package com.ldf.wanandroidcompose.ui.home

import android.content.Context
import android.os.Build
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.guru.composecookbook.carousel.PagerState
import com.ldf.wanandroidcompose.R
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.ColorPallet
import com.ldf.wanandroidcompose.ui.theme.blue500
import com.ldf.wanandroidcompose.ui.theme.green500
import com.ldf.wanandroidcompose.ui.theme.orange500
import com.ldf.wanandroidcompose.ui.theme.purple
import com.ldf.wanandroidcompose.ui.theme.typography
import com.ldf.wanandroidcompose.ui.utils.TestTags
import com.ldf.wanandroidcompose.ui.viewmodel.HomeViewModel
import com.ldf.wanandroidcompose.ui.widget.carousel.CarouselDot
import com.ldf.wanandroidcompose.ui.widget.carousel.Pager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * @Author : dongfang
 * @Created Time : 2023-05-31  17:29
 * @Description:
 */


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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
//        topBar = {
//            TopAppBar(
//                backgroundColor = MaterialTheme.colorScheme.primary,
//                title = {
//                    Text(text = "Wan Android", style = MaterialTheme.typography.titleLarge)
//                },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            appThemeState.value = appThemeState
//                                .value.copy(darkTheme = !appThemeState.value.darkTheme)
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_sleep),
//                            contentDescription = stringResource(id = R.string.cd_dark_theme)
//                        )
//                    }
//                    ChangeColorIconButton(coroutineScope, showMenu)
//                }
//            )
//        },
        content = { paddingValues ->
            HomeScreenContent(
                isDarkTheme = appThemeState.value.darkTheme,
                showMenu = showMenu,
                modifier = Modifier.padding(paddingValues),
                onPalletChange = { newPalletSelected ->
                    // Events can be and should be passed to as upper layer as possible here
                    // we are just passing to till HomeScreen.
                    appThemeState.value = appThemeState.value.copy(pallet = newPalletSelected)
                    showMenu.value = false
                },
                homeViewModel = homeViewModel
            )
        })
}

@Composable
fun HomeScreenContent(
    isDarkTheme: Boolean,
    showMenu: MutableState<Boolean>,
    onPalletChange: (ColorPallet) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel
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
    Box(modifier = modifier) {
        Column {
            PrepareFirstPager(pagerState, itemList, selectedPage)
            SwipeRefreshList(homeViewModel, context, isDarkTheme, isWiderScreen)
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
                MaterialTheme.colorScheme.primary,
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

@Composable
fun PalletMenu(
    modifier: Modifier,
    onPalletChange: (ColorPallet) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .animateContentSize(),
        ) {
            MenuItem(green500, "Green") {
                onPalletChange.invoke(ColorPallet.GREEN)
            }
            MenuItem(purple, "Purple") {
                onPalletChange.invoke(ColorPallet.PURPLE)
            }
            MenuItem(orange500, "Orange") {
                onPalletChange.invoke(ColorPallet.ORANGE)
            }
            MenuItem(blue500, "Blue") {
                onPalletChange.invoke(ColorPallet.BLUE)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MenuItem(dynamicLightColorScheme(LocalContext.current).primary, "Dynamic") {
                    onPalletChange.invoke(ColorPallet.WALLPAPER)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangeColorIconButton(
    coroutineScope: CoroutineScope,
    showMenu: MutableState<Boolean>
) {
    val accessibilityManager = LocalContext.current.getSystemService(Context.ACCESSIBILITY_SERVICE)
            as android.view.accessibility.AccessibilityManager
    IconButton(onClick = {
        if (accessibilityManager.isEnabled && accessibilityManager.isTouchExplorationEnabled) {
            coroutineScope.launch {}
        } else {
            showMenu.value = !showMenu.value
        }
    }) {
        Icon(
            imageVector = Icons.Default.Palette, contentDescription = stringResource(
                id = R.string.cd_change_color
            )
        )
    }
}

@Composable
fun MenuItem(color: Color, name: String, onPalletChange: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onPalletChange),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.FiberManualRecord,
            tint = color,
            contentDescription = null
        )
        Text(text = name, modifier = Modifier.padding(8.dp))
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewHomeScreen() {
    val state = remember {
        mutableStateOf(AppThemeState(false, ColorPallet.GREEN))
    }
    val chooseColorBottomModalState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

//    HomeScreen(null, state)
}