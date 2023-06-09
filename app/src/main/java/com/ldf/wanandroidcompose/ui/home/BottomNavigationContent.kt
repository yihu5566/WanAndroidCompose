package com.ldf.wanandroidcompose.ui.home

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ldf.wanandroidcompose.ui.theme.Nav

/**
 * @Author : dongfang
 * @Created Time : 2023-06-09  15:19
 * @Description:
 */

//底部导航栏列表
val items = listOf(
    Nav.BottomNavScreen.HomeScreen,
    Nav.BottomNavScreen.ProjectScreen,
    Nav.BottomNavScreen.SquareScreen,
    Nav.BottomNavScreen.PublicNumScreen,
    Nav.BottomNavScreen.MineScreen
)

@Composable
fun BottomNavigationContent(
    bottomNavScreen: Nav.BottomNavScreen,
    navHostController: NavHostController
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        items.forEach { bottomNavScreenItem: Nav.BottomNavScreen ->
            //记录动画
            val translationY = remember { Animatable(0F) }

            //开启线程执行动画
            LaunchedEffect(bottomNavScreen) {
                if (bottomNavScreenItem == bottomNavScreen)
                    translationY.animateTo(-4F)
                else translationY.animateTo(0F)
            }

            BottomNavigationItem(
                icon = {
                    androidx.compose.material.Icon(
                        painterResource(bottomNavScreenItem.id),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .offset(
                                0.dp,
                                //上下偏移
                                translationY.value.dp
                            ),
                    )
                },
                //选中选项的颜色 (text\icon\波纹)
                selectedContentColor = MaterialTheme.colors.primary,
                //未选中选项的颜色 (text\icon\波纹)
                unselectedContentColor = MaterialTheme.colors.secondaryVariant,
                label = { androidx.compose.material.Text(stringResource(bottomNavScreenItem.resourceId)) },
                selected = bottomNavScreen == bottomNavScreenItem,
                onClick = {
                    //判断是否是当前的route,如果是就不做处理
                    if (bottomNavScreenItem == bottomNavScreen) {
                        return@BottomNavigationItem
                    }
                    //记录当前的Item
                    Nav.bottomNavRoute.value = bottomNavScreenItem

                    navHostController.navigate(bottomNavScreenItem.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        //避免重建
                        launchSingleTop = true
                        //重新选择以前选择的项目时，恢复状态
                        restoreState = true
                    }
                },
                modifier = Modifier.background(MaterialTheme.colors.background)
            )
        }
    }
}
