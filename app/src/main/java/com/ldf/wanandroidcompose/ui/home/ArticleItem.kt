package com.ldf.wanandroidcompose.ui.home

import android.content.Context
import android.text.Html
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.ldf.wanandroidcompose.R
import com.ldf.wanandroidcompose.data.bean.Article

/**
 * @Author : dongfang
 * @Created Time : 2023-06-05  15:55
 * @Description:
 */
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewArticleItem() {
//    ArticleItem(null, LocalContext.current, true, true)
}

@Composable
fun ArticleItem(
    itemBean: Article,
    context: Context,
    isWiderScreen: Boolean,
    isWiderScreen1: Boolean,
    onClick: () -> Unit = {}

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .testTag("button-${itemBean.title}")
            .background(color = Color.White)
            .clickable(onClick = onClick)
    ) {
        Row {
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
                        .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(15))
                        .padding(3.dp),
                    style = TextStyle(color = Color.Red)
                )
            }
            Spacer(modifier = Modifier.width(width = 10.dp))
            if (itemBean.fresh) {
                Text(
                    text = "新",
                    modifier = Modifier
                        .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(15))
                        .padding(3.dp),
                    style = TextStyle(color = Color.Red)
                )
            }
            if (itemBean.tags.isNotEmpty()) {
                Text(
                    text = itemBean.tags[0].name,
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(5.dp),
                )
            }

            Text(
                text = itemBean.niceDate,
                modifier = Modifier
                    .padding(5.dp),
            )
        }
        AndroidView(
            factory = { context -> TextView(context) },
            update = {
                it.text = HtmlCompat.fromHtml(itemBean.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }
        )

        Spacer(modifier = Modifier.height(height = 10.dp))

        Row {
            AndroidView(
                factory = { context -> TextView(context) },
                update = {
                    it.text = HtmlCompat.fromHtml(
                        itemBean.superChapterName + '·' + itemBean.chapterName,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                }
            )
            Spacer(modifier = Modifier.weight(weight = 1f, true))
            Icon(
                painter = painterResource(id = R.drawable.ic_un_collect),
                contentDescription = null,
            )
        }
        Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
    }
}