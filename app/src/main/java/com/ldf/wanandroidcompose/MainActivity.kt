package com.ldf.wanandroidcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
import com.ldf.wanandroidcompose.ui.home.BottomNavigationContent
import com.ldf.wanandroidcompose.ui.home.MainTopBar
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.ColorPallet
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.theme.SystemUiController
import com.ldf.wanandroidcompose.ui.theme.WanAndroidComposeTheme
import com.ldf.wanandroidcompose.utils.CommonConstant
import com.ldf.wanandroidcompose.utils.LocalDataManage

class MainActivity : ComponentActivity() {
    private val themeName =
        LocalDataManage.getSyncData(CommonConstant.THEME, ColorPallet.GREEN.name)
    private val themeSelect = mutableStateOf(AppThemeState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val switchTheme = switchTheme(themeName)
        themeSelect.value.pallet = switchTheme

        setContent {
            //设置为沉浸式状态栏
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { themeSelect }
            WanAndroidComposeTheme(
                darkTheme = appTheme.value.darkTheme,
                colorPallet = appTheme.value.pallet
            ) {
                systemUiController.setStatusBarColor(
                    color = MaterialTheme.colors.onSecondary,
                    darkIcons = appTheme.value.darkTheme
                )
                ProvideWindowInsets {
                    MainAppContent(appTheme, onFinish = { finish() })
                }
            }
        }
    }

    fun switchTheme(themeName: String): ColorPallet {
        var saveTheme: ColorPallet
        when (themeName) {
            ColorPallet.PURPLE.name -> {
                saveTheme = ColorPallet.PURPLE
            }

            ColorPallet.ORANGE.name -> {
                saveTheme = ColorPallet.ORANGE
            }

            ColorPallet.BLUE.name -> {
                saveTheme = ColorPallet.BLUE
            }

            else -> {
                saveTheme = ColorPallet.GREEN
            }
        }
        return saveTheme
    }
}

/**
 * 内容
 */
@Composable
fun MainAppContent(
    appThemeState: MutableState<AppThemeState>,
    navHostController: NavHostController = rememberNavController(),
    onFinish: () -> Unit
) {
    //返回back堆栈的顶部条目
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    //返回当前route
    val currentRoute = navBackStackEntry?.destination?.route ?: Nav.BottomNavScreen.HomeScreen.route
    if (isMainScreen(currentRoute)) {

        Scaffold(
            contentColor = MaterialTheme.colors.background,
            topBar = {
                Column {
                    //内容不挡住状态栏 如果不设置颜色这里会自己适配，但可能产生闪烁
                    Spacer(
                        modifier = Modifier
                            .background(MaterialTheme.colors.primary)
                            .statusBarsHeight()
                            .fillMaxWidth()
                    )

                    MainTopBar(Nav.bottomNavRoute.value, navHostController)
                }
            },
            bottomBar = {
                Column {
                    BottomNavigationContent(
                        Nav.bottomNavRoute.value,
                        navHostController
                    )
                    //内容不挡住导航栏 如果不设置颜色这里会自己适配，但可能产生闪烁
                    Spacer(
                        modifier = Modifier
                            .background(MaterialTheme.colors.primary)
                            .navigationBarsHeight()
                            .fillMaxWidth()
                    )
                }
            },
            content = {
                NavigationHost(appThemeState, navHostController, it, onFinish)
            }
        )
    } else {
        NavigationHost(appThemeState, navHostController = navHostController, onFinish = onFinish)
    }
}

/**
 * 是否是首页的内容
 */
fun isMainScreen(route: String): Boolean = when (route) {
    Nav.BottomNavScreen.HomeScreen.route,
    Nav.BottomNavScreen.ProjectScreen.route,
    Nav.BottomNavScreen.SquareScreen.route,
    Nav.BottomNavScreen.PublicNumScreen.route,
    Nav.BottomNavScreen.MineScreen.route -> true

    else -> false
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val appThemeState = remember { mutableStateOf(AppThemeState(false, ColorPallet.GREEN)) }
    MainAppContent(appThemeState, onFinish = {})
}