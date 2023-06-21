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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ldf.wanandroidcompose.R

/**
 * @Author : dongfang
 * @Created Time : 2023-06-01  09:27
 * @Description:
 */
@Composable
fun ProfileScreen(navHost: NavHostController) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
    ) {
        TopWidget()
        BottomWidget()
    }
}

@Composable
fun BottomWidget() {
    Spacer(modifier = Modifier.height(60.dp))
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(modifier = Modifier.padding(top = 10.dp)) {
            ProfileItem(painterResource(R.mipmap.ic_jifen), "我的积分", 10) {}
            ProfileItem(painterResource(R.drawable.ic_collect), "我的收藏") {}
            ProfileItem(painterResource(R.mipmap.ic_wenzhang), "我的文章") {}
            ProfileItem(painterResource(R.mipmap.ic_shezhi), "系统设置") {}
        }
    }

}

@Composable
fun TopWidget(name: String = "请先登录") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .height(80.dp)
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
            Text(name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Row {
                Text(text = "id:")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "排名:")

            }
        }
    }


}

@Composable
fun ProfileItem(
    painter: Painter = painterResource(R.drawable.ic_collect),
    title: String,
    num: Int = -1,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .height(30.dp)
            .clickable(onClick = onClick),
    ) {
        Icon(
            painter,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(16.dp),
            tint = MaterialTheme.colors.secondaryVariant,
            contentDescription = null
        )
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        if (num != -1) {
            Text(
                text = "当前积分:",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "$num",
                style = TextStyle(color = MaterialTheme.colors.primary, fontSize = 16.sp),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Icon(
            painterResource(R.mipmap.ic_right),
            modifier = Modifier
                .padding(start = 8.dp)
                .size(16.dp), contentDescription = null
        )

    }

}
