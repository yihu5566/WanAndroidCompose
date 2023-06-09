package com.ldf.wanandroidcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
import com.ldf.wanandroidcompose.ui.home.BottomNavigationContent
import com.ldf.wanandroidcompose.ui.home.MainTopBar
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.ColorPallet
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.theme.SystemUiController
import com.ldf.wanandroidcompose.ui.theme.WanAndroidComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainAppContent(appTheme, onFinish = { finish() })
            }
        }
    }
}

@Composable
fun BaseView(
    appThemeState: AppThemeState,
    systemUiController: SystemUiController?,
    content: @Composable () -> Unit
) {
    WanAndroidComposeTheme(
        darkTheme = appThemeState.darkTheme,
        colorPallet = appThemeState.pallet
    ) {
        systemUiController?.setStatusBarColor(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            darkIcons = appThemeState.darkTheme
        )
        content()
    }
}

/**
 * 内容
 */
@OptIn(ExperimentalMaterialApi::class)
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
    val homeScreenState = rememberSaveable { mutableStateOf(BottomNavType.HOME) }
    val chooseColorBottomModalState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    Scaffold(
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                //内容不挡住状态栏 如果不设置颜色这里会自己适配，但可能产生闪烁
                Spacer(
                    modifier = Modifier
                        .background(androidx.compose.material.MaterialTheme.colors.primary)
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
                        .background(androidx.compose.material.MaterialTheme.colors.primary)
                        .navigationBarsHeight()
                        .fillMaxWidth()
                )
            }
        },
        content = {
            NavigationHost(appThemeState, navHostController, it, onFinish)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val appThemeState = remember { mutableStateOf(AppThemeState(false, ColorPallet.GREEN)) }
    BaseView(appThemeState.value, null) {
        MainAppContent(appThemeState, onFinish = {})
    }
}