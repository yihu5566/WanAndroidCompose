package com.ldf.wanandroidcompose.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @Author : dongfang
 * @Created Time : 2023-06-19  14:33
 * @Description:
 */
@Composable
fun SimpleCard(
    cardHeight: Dp = 120.dp,
    elevation: Dp = 3.dp,
    shape: RoundedCornerShape = RoundedCornerShape(6.dp),
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier
            //外边距
            .padding(bottom = 5.dp, start = 8.dp, end = 8.dp, top = 5.dp).fillMaxWidth()
            .height(cardHeight),
        elevation = elevation,
        backgroundColor = MaterialTheme.colors.background,
        shape = shape,
        border = if (isSystemInDarkTheme()) BorderStroke(1.dp, Color.White) else null
    ) {
        content()
    }

}

@Composable
fun SimpleCard(
    elevation: Dp = 3.dp,
    shape: RoundedCornerShape = RoundedCornerShape(6.dp),
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier
            //外边距
            .padding(bottom = 5.dp, start = 8.dp, end = 8.dp, top = 5.dp).fillMaxWidth(),
        elevation = elevation,
        backgroundColor = MaterialTheme.colors.background,
        shape = shape,
        border = if (isSystemInDarkTheme()) BorderStroke(1.dp, Color.White) else null
    ) {
        content()
    }

}