package com.ldf.wanandroidcompose.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ldf.wanandroidcompose.R

/**
 * @Author : dongfang
 * @Created Time : 2023-07-05  14:32
 * @Description:
 */

@Composable
fun CollectCompose(isCollect: Boolean, onCollectClick: () -> Unit) {
    if (isCollect) {
        Icon(
            painter = painterResource(id = R.drawable.ic_collect),
            tint = MaterialTheme.colors.primaryVariant,
            contentDescription = null,
            modifier = Modifier.clickable { onCollectClick() }
        )
    } else {
        Icon(
            painter = painterResource(id = R.drawable.ic_un_collect),
            tint = MaterialTheme.colors.primaryVariant,
            contentDescription = null,
            modifier = Modifier.clickable { onCollectClick() }
        )
    }
}