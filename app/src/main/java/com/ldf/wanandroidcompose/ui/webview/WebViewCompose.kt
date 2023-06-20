package com.ldf.wanandroidcompose.ui.webview

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.ldf.wanandroidcompose.ui.widget.AppBar
import com.ldf.wanandroidcompose.ui.widget.BaseScreen

/**
 * @Author : dongfang
 * @Created Time : 2023-06-20  13:37
 * @Description:
 */
@Composable
fun WebViewCompose(navHostController: NavHostController, url: String) {
    BaseScreen {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppBar("玩Android", leftIcon = Icons.Default.ArrowBack, onLeftClick = {
                navHostController.navigateUp()
            })
            AndroidView({ context: Context ->
                WebView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }, update = {
                //update方法是一个callback, inflate之后会执行, 读取的状态state值变化后也会被执行
                it.apply {
                    loadUrl(url)
                }
            })
        }
    }

}