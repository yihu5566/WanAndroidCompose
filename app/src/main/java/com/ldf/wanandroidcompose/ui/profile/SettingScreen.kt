package com.ldf.wanandroidcompose.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.ColorPallet
import com.ldf.wanandroidcompose.ui.theme.blue500
import com.ldf.wanandroidcompose.ui.theme.green500
import com.ldf.wanandroidcompose.ui.theme.orange500
import com.ldf.wanandroidcompose.ui.theme.purple500
import com.ldf.wanandroidcompose.utils.CacheDataManager
import com.ldf.wanandroidcompose.utils.CommonConstant
import com.ldf.wanandroidcompose.utils.HeadingSection
import com.ldf.wanandroidcompose.utils.LocalDataManage
import com.ldf.wanandroidcompose.utils.TitleText
import com.ldf.wanandroidcompose.ui.viewmodel.SettingViewModel
import com.ldf.wanandroidcompose.ui.widget.AppBar
import com.ldf.wanandroidcompose.ui.widget.BaseScreen
import com.ldf.wanandroidcompose.ui.widget.SimpleAlertDialog

/**
 * @Author : dongfang
 * @Created Time : 2023-06-26  13:27
 * @Description:
 */
@Composable
fun SettingScreen(
    navHostController: NavHostController,
    appThemeState: MutableState<AppThemeState>
) {
    val settingViewModel: SettingViewModel = viewModel()
    BaseScreen {
        Scaffold(topBar = {
            AppBar(title = "设置", leftIcon = Icons.Default.ArrowBack, onLeftClick = {
                navHostController.navigateUp()
            })
        }, content = { paddingValues: PaddingValues ->
            SettingCenterScreen(navHostController, settingViewModel, paddingValues, appThemeState)
        })
    }
}

@Composable
fun SettingCenterScreen(
    navHostController: NavHostController,
    settingViewModel: SettingViewModel,
    paddingValues: PaddingValues,
    appThemeState: MutableState<AppThemeState>
) {

    LogUtils.d("padding${paddingValues.calculateTopPadding()}")
    val context = LocalContext.current
    //清除缓存弹窗
    var cacheDataState by remember { mutableStateOf(false) }
    if (cacheDataState) {
        //弹窗,关闭之后重新设置为false,下次可以继续监听
        SimpleAlertDialog("温馨提示", "确定清理缓存吗", confirmStr = "清理", confirmClick = {
            CacheDataManager.clearAllCache(context)
        }) { cacheDataState = false }
    }

    //退出登录弹窗
    var exitLoginState by remember { mutableStateOf(false) }
    if (exitLoginState) {
        SimpleAlertDialog("温馨提示", "确定退出登录吗", confirmStr = "退出", confirmClick = {
            settingViewModel.logout {
                navHostController.navigateUp()
            }
        }) { exitLoginState = false }
    }
    //主题颜色弹窗
    var themeColorState by remember { mutableStateOf(false) }
    if (themeColorState) {
        SimpleAlertDialog("主题颜色设置", textCompose = {
            ThemeSelectedScreen(appThemeState)
        }) { themeColorState = false }
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            TitleText(title = "基本设置")
            HeadingSection(
                title = "清除缓存",
                subtitle = CacheDataManager.getTotalCacheSize(context)
            ) {
                cacheDataState = true
            }
            HeadingSection(title = "退出", subtitle = "退出登录") {
                exitLoginState = true
            }
            Divider()
        }

        item {
            TitleText(title = "其他设置")
            Row(verticalAlignment = Alignment.CenterVertically) {
                HeadingSection(Modifier.weight(1f), "主题", "设置主题颜色") {
                    themeColorState = true
                }
                Surface(
                    modifier = Modifier.size(30.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colors.primary,
                    content = {}
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                var dark = if (appThemeState.value.darkTheme) "夜间" else "白天"
                HeadingSection(Modifier.weight(1f), "夜间模式", dark)
                Switch(checked = appThemeState.value.darkTheme, onCheckedChange = {
                    appThemeState.value = appThemeState.value.copy(darkTheme = it)
                })
            }

            Divider()
        }
        item {
            TitleText(title = "关于")
            HeadingSection(
                title = "版本",
                subtitle = "当前版本：${AppUtils.getAppVersionName()}"
            )
            HeadingSection(
                title = "作者",
                subtitle = "yihu5566"
            )
            HeadingSection(
                title = "GitHub地址",
                subtitle = "https://github.com/yihu5566/WanAndroidCompose"
            )
            HeadingSection(title = "版权声明", subtitle = "仅供个人及非商业用途使用")
        }

    }

}

/**
 * 主题选择布局
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ThemeSelectedScreen(appThemeState: MutableState<AppThemeState>) {
    //aspectRatio 宽高1:1
    val modifier = Modifier
        .aspectRatio(1f)
        .padding(4.dp)
    //垂直GridList
    LazyVerticalGrid(
        //每行的数量
        columns = GridCells.Fixed(4)
    ) {
        itemsIndexed(ColorPallet.values()) { index: Int, theme: ColorPallet ->
            Surface(
                border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                modifier = modifier.clickable(onClick = {
                    //保存主题颜色
                    appThemeState.value = appThemeState.value.copy(pallet = theme)
                    LocalDataManage.saveSyncStringData(CommonConstant.THEME, theme.name)
                }),
                shape = CircleShape,
                color =
                when (theme.name) {
                    ColorPallet.GREEN.name -> {
                        green500
                    }

                    ColorPallet.PURPLE.name -> {
                        purple500
                    }

                    ColorPallet.ORANGE.name -> {
                        orange500
                    }

                    ColorPallet.BLUE.name -> {
                        blue500
                    }

                    else -> {
                        green500
                    }
                },
            ) {
                //如果是当前选中的主题
                if (appThemeState.value.pallet == theme) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colors.background
                        )
                    }
                }
            }
        }
    }
}


