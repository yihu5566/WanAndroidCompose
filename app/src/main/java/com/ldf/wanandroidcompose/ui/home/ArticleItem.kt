package com.ldf.wanandroidcompose.ui.home

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Tag
import com.ldf.wanandroidcompose.ui.widget.CollectCompose

/**
 * @Author : dongfang
 * @Created Time : 2023-06-05  15:55
 * @Description:
 */
@Preview
@Composable
fun PreviewArticleItem() {
    ArticleItem(
        Article(
            niceDate = "2020-2-2 20:00",
            tags = listOf(Tag("kk", "")),
            fresh = true,
            author = "yihu",
            type = 1,
            title = "oooooo", superChapterName = "d", chapterName = "fff", collect = true
        )
    )
}

@Composable
fun ArticleItem(
    itemBean: Article,
    isCollect: Boolean = false,
    onClick: () -> Unit = {},
    onCollectClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = MaterialTheme.colors.background)
            .clickable(onClick = onClick)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (itemBean.author.isNotEmpty()) {
                Text(
                    text = itemBean.author,
                    modifier = Modifier
                        .padding(5.dp),
                )
            }

            if (itemBean.type == 1) {
                Text(
                    text = "置顶",
                    modifier = Modifier
                        .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(10))
                        .padding(end = 3.dp, start = 3.dp),
                    style = TextStyle(color = Color.Red)
                )
            }
            Spacer(modifier = Modifier.width(width = 10.dp))
            if (itemBean.fresh) {
                Text(
                    text = "新",
                    modifier = Modifier
                        .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(10))
                        .padding(end = 3.dp, start = 3.dp),
                    style = TextStyle(color = Color.Red)
                )
            }
            if (itemBean.tags.isNotEmpty()) {
                Text(
                    text = itemBean.tags[0].name,
                    modifier = Modifier
                        .padding(5.dp),
                )
            }
            Spacer(modifier = Modifier.weight(1f, true))
            Text(
                text = itemBean.niceDate,
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.h6.copy(color = Color.Gray, fontSize = 10.sp)
            )
        }
        var textColor = MaterialTheme.colors.onSecondary.toArgb()
        AndroidView(
            factory = { context -> TextView(context) },
            update = {
                it.text = HtmlCompat.fromHtml(itemBean.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
                it.setTextColor(textColor)
            }
        )

        Spacer(modifier = Modifier.height(height = 10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            AndroidView(
                factory = { context -> TextView(context) },
                update = {
                    it.text = HtmlCompat.fromHtml(
                        itemBean.superChapterName + '·' + itemBean.chapterName,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                    it.setTextColor(textColor)
                }
            )
            Spacer(modifier = Modifier.weight(weight = 1f, true))
            CollectCompose(isCollect, onCollectClick)
        }
    }
}


