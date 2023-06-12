package com.ldf.wanandroidcompose.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.ui.viewmodel.ProjectViewModel
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.widget.AppBar

/**
 * @Author : dongfang
 * @Created Time : 2023-06-09  16:27
 * @Description:
 */
@Composable
fun MainTopBar(bottomNavScreen: Nav.BottomNavScreen, navHostController: NavHostController) {
    when (bottomNavScreen) {
        //首页
        Nav.BottomNavScreen.HomeScreen -> {
            AppBar(
                "首页",
                rightIcon = Icons.Default.Search,
                //跳转到搜索页面
                onRightClick = { navHostController.navigate(KeyNavigationRoute.SEARCH.route) })
        }
        //项目
        Nav.BottomNavScreen.ProjectScreen -> {

            val projectViewModel: ProjectViewModel = viewModel()

            //请求项目列表数据
//            projectViewModel.getProjectTreeData()

//            val projectTreeData = projectViewModel.projectTreeData.observeAsState()

            //顶部指示器
//            ProjectTab(Nav.projectTopBarIndex, projectTreeData)
        }
        //广场
        Nav.BottomNavScreen.SquareScreen -> {
            //顶部指示器
//            SquareTab(Nav.squareTopBarIndex)
        }
        //公众号
        Nav.BottomNavScreen.PublicNumScreen -> {

//            val publicNumViewModel: PublicNumViewModel = viewModel()
//
//            //请求公众号列表数据
//            publicNumViewModel.getPublicNumChapterData()
//
//            val publicNumChapterData = publicNumViewModel.publicNumChapter.observeAsState()
//
//            //顶部指示器
//            PublicNumTab(Nav.publicNumIndex, publicNumChapterData)

        }
        //我的
        Nav.BottomNavScreen.MineScreen -> {
            AppBar(elevation = 0.dp)
        }
    }
}