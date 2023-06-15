package com.ldf.wanandroidcompose.ui.project

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ldf.wanandroidcompose.R
import com.ldf.wanandroidcompose.data.bean.Article
import java.text.SimpleDateFormat

/**
 * @Author : dongfang
 * @Created Time : 2023-06-12  16:32
 * @Description:
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun projectItemWidget(itemBean: Article, context: Context) {

    Column(modifier = Modifier) {
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
                    .fillMaxHeight()
                    .width(130.dp)
                    .padding(end = 4.dp)
            )
            Column {
                androidx.compose.material.Text(
                    text = itemBean.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    fontSize = 17.sp,
                    //超长以...结尾
                    overflow = TextOverflow.Ellipsis
                )

                androidx.compose.material.Text(
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
            Icon(
                painter = painterResource(id = R.drawable.ic_un_collect),
                contentDescription = null,
            )
        }
    }

}
