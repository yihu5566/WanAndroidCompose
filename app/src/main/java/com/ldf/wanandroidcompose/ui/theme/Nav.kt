package com.ldf.wanandroidcompose.ui.theme

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import com.ldf.wanandroidcompose.R

/**
 * 导航相关
 */
object Nav {

    //密封类关联目的地路线和字符串资源
    sealed class BottomNavScreen(
        val route: String,
        @StringRes val resourceId: Int,
        @DrawableRes val id: Int
    ) {
        object HomeScreen :
            BottomNavScreen("home", R.string.navigation_item_home, R.drawable.ic_tab_home)

        object ProjectScreen :
            BottomNavScreen("project", R.string.navigation_item_project, R.drawable.ic_tab_project)

        object SquareScreen :
            BottomNavScreen("square", R.string.navigation_item_square, R.drawable.ic_tab_square)

        object PublicNumScreen : BottomNavScreen(
            "publicNum",
            R.string.navigation_item_public_num,
            R.drawable.ic_tab_wechat
        )

        object MineScreen :
            BottomNavScreen("mine", R.string.navigation_item_profile, R.drawable.ic_tab_mine)
    }

    //记录BottomNav当前的Item
    val bottomNavRoute = mutableStateOf<BottomNavScreen>(BottomNavScreen.HomeScreen)

    //是否点击两次返回的activity
    var twoBackFinishActivity = false

    //项目页面指示器index
    val projectTopBarIndex = mutableStateOf(0)

    //广场页面指示器index
    val squareTopBarIndex = mutableStateOf(0)

    //公众号页面指示器index
    val publicNumIndex = mutableStateOf(0)

}