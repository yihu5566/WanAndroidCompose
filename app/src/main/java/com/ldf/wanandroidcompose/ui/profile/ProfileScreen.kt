package com.ldf.wanandroidcompose.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldf.wanandroidcompose.KeyNavigationRoute
import com.ldf.wanandroidcompose.R
import com.ldf.wanandroidcompose.base.App
import com.ldf.wanandroidcompose.ui.utils.LocalDataManage

/**
 * @Author : dongfang
 * @Created Time : 2023-06-01  09:27
 * @Description:
 */
@Composable
fun ProfileScreen(navHost: NavHostController) {

    val loginViewModel: LoginViewModel = viewModel()


    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
    ) {
        //监听 刷新个人信息数据
        LaunchedEffect(App.appViewModel.userEvent.value) {
            //获取个人积分数据
            loginViewModel.getUserInfoIntegral()
            loginViewModel.getUserInfo()
        }
        TopWidget(navHost, loginViewModel)
        BottomWidget(navHost, loginViewModel)
    }
}

@Composable
fun BottomWidget(navHost: NavHostController, loginViewModel: LoginViewModel) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        val user = loginViewModel.userData.observeAsState()

        Column(modifier = Modifier.padding(top = 10.dp)) {
            val observeAsState = loginViewModel.userIntegralData.observeAsState()
            ProfileItem(
                painterResource(R.mipmap.ic_jifen),
                "我的积分",
                observeAsState.value?.coinCount ?: -1
            ) {
            }
            ProfileItem(painterResource(R.drawable.ic_collect), "我的收藏") {
                if (user.value == null) {
                    ToastUtils.showLong("请先登录！！")
                }
            }
            ProfileItem(painterResource(R.mipmap.ic_wenzhang), "我的文章") {
                if (user.value == null) {
                    ToastUtils.showLong("请先登录！！")
                }
            }
            ProfileItem(painterResource(R.mipmap.ic_shezhi), "系统设置") {
                navHost.navigate(KeyNavigationRoute.SETTING.route)
            }
        }
    }

}

@Composable
fun TopWidget(navHost: NavHostController, loginViewModel: LoginViewModel) {
    val user = loginViewModel.userData.observeAsState()
    val observeAsState = loginViewModel.userIntegralData.observeAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, bottom = 60.dp)
            .height(80.dp)
            .clickable {
                if (user.value == null) {
                    navHost.navigate(KeyNavigationRoute.LOGIN.route)
                }
            }
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(80.dp)
        ) {
            Image(painterResource(R.mipmap.ic_account), contentDescription = null)
        }
        Column(
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                observeAsState.value?.username ?: "请登录",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Row {
                Text(text = "id:${observeAsState.value?.userId ?: ""}")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "排名:${observeAsState.value?.rank ?: ""}")
            }
        }
    }
}

