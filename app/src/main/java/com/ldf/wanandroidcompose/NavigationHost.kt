package com.ldf.wanandroidcompose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ldf.wanandroidcompose.ui.home.HomeScreen
import com.ldf.wanandroidcompose.ui.profile.ProfileScreen
import com.ldf.wanandroidcompose.ui.square.SquareScreen
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.utils.StatsBarUtil
import com.ldf.wanandroidcompose.ui.utils.TwoBackFinish
import com.ldf.wanandroidcompose.ui.wechat.WechatScreen

/**
 * @Author : dongfang
 * @Created Time : 2023-06-09  16:41
 * @Description:
 */
@Composable
fun NavigationHost(
    appThemeState: MutableState<AppThemeState>,
    navHostController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    NavHost(
        navController = navHostController,
        startDestination = KeyNavigationRoute.MAIN.route,
        modifier = Modifier.padding(paddingValues)
    ) { //主页面
        navigation(
            route = KeyNavigationRoute.MAIN.route,
            startDestination = Nav.BottomNavScreen.HomeScreen.route
        ) {
            //首页
            composable(Nav.BottomNavScreen.HomeScreen.route) {

                //系统颜色的状态栏
                StatsBarUtil().StatsBarColor(false)

                HomeScreen(navHostController, appThemeState)

                //点击两次返回才关闭app
//                    BackHandler {
//                        TwoBackFinish().execute(context, onFinish)
//                    }
            }
            //项目页面
            composable(Nav.BottomNavScreen.ProjectScreen.route) {

                //系统颜色的状态栏
                StatsBarUtil().StatsBarColor(false)

                ProfileScreen(navHostController)

                //点击两次返回才关闭app
                BackHandler {
                    TwoBackFinish().execute(context, onFinish)
                }
            }
            //广场页面
            composable(Nav.BottomNavScreen.SquareScreen.route) {

                //系统颜色的状态栏
                StatsBarUtil().StatsBarColor(false)

                SquareScreen(navHostController)

                //点击两次返回才关闭app
                BackHandler {
                    TwoBackFinish().execute(context, onFinish)
                }
            }
            //公众号页面
            composable(Nav.BottomNavScreen.PublicNumScreen.route) {

                //系统颜色的状态栏
                StatsBarUtil().StatsBarColor(false)

                WechatScreen(navHostController)

                //点击两次返回才关闭app
                BackHandler {
                    TwoBackFinish().execute(context, onFinish)
                }
            }

            //我的页面
            composable(Nav.BottomNavScreen.MineScreen.route) {

                //系统颜色的状态栏
                StatsBarUtil().StatsBarColor(false)

                ProfileScreen(navHostController)

                //点击两次返回才关闭app
                BackHandler {
                    TwoBackFinish().execute(context, onFinish)
                }
            }
        }
    }

}


/**
 * 页面跳转关键类
 */
enum class KeyNavigationRoute(
    val route: String
) {
    //主页面
    MAIN("main"),

    //登录页面
    LOGIN("login"),

    //注册页面
    REGISTER("register"),

    //积分排行
    INTEGRAL_RANK("integral_rank"),

    //我的收藏
    MY_COLLECT("my_collect"),

    //我的文章
    MY_SHARE_ARTICLES("my_share_articles"),

    //设置
    SETTING("setting"),

    //H5
    WEBVIEW("webview"),

    //搜索
    SEARCH("search"),

    //学习 - 动画
    LEARN_ANIMATION("learn_animation")
}