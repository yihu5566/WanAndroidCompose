package com.ldf.wanandroidcompose.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ldf.wanandroidcompose.R

/**
 * @Author : dongfang
 * @Created Time : 2023-06-26  13:43
 * @Description:
 */
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