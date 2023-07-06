package com.ldf.wanandroidcompose.ui.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.ui.widget.CollectCompose

/**
 * @Author : dongfang
 * @Created Time : 2023-06-12  16:32
 * @Description:
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun projectItemWidget(
    itemBean: Article,
    isCollect: Boolean = false,
    onCollectClick: () -> Unit = {},
    onClick: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(bottom = 6.dp, top = 6.dp)
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Row {
            if (itemBean.author.isNotEmpty()) {
                Text(
                    text = itemBean.author,
                    modifier = Modifier
                        .padding(5.dp),
                )
            }
            Spacer(modifier = Modifier.weight(weight = 1f, true))
            Text(text = TimeUtils.millis2String(itemBean.publishTime))
        }
        Row {
            GlideImage(
                model = itemBean.envelopePic,
                contentDescription = "",
                modifier = Modifier
                    .wrapContentWidth()
                    .height(130.dp)
                    .padding(end = 4.dp)
            )

            Column(modifier = Modifier.padding(horizontal = 6.dp)) {
                Text(
                    text = itemBean.title,
                    style = MaterialTheme.typography.h4,
                    maxLines = 2,
                    //超长以...结尾
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = itemBean.desc,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 3.dp),
                    maxLines = 4,
                    fontSize = 14.sp,
                    //超长以...结尾
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${itemBean.superChapterName}·${itemBean.chapterName}")
            CollectCompose(isCollect, onCollectClick)
        }
    }

}
