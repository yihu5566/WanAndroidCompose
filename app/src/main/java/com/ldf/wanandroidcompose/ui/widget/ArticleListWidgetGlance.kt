package com.ldf.wanandroidcompose.ui.widget

import android.content.Context
import androidx.compose.runtime.key
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.MainActivity
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.Article

/**
 * @Author : dongfang
 * @Created Time : 2023-09-15  11:00
 * @Description:
 */
class ArticleListWidgetGlance : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val articleList = getArticleList()
        provideContent {
            GlanceTheme {
                Column(
                    modifier = GlanceModifier.fillMaxSize().background(GlanceTheme.colors.surface)
                        .cornerRadius(10.dp)
                        .clickable(
                            actionStartActivity<MainActivity>()
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (articleList.isNotEmpty()) {
                        key(LocalSize.current) {
                            LazyColumn(
                                modifier = GlanceModifier.fillMaxSize().padding(horizontal = 10.dp)
                            ) {
                                items(articleList) {
                                    Text(text = it.title)
                                }
                            }
                        }

                    } else {
                        Text(
                            text = "重新加载。。。",
                            modifier = GlanceModifier.padding(10.dp),
                        )
                        Button(
                            text = "reload",
                            actionRunCallback<RefreshAction>()
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val ARTICLE_DATA: String = "ARTICLE_DATA"
    }

    private suspend fun getArticleList(): List<Article> {
        LogUtils.i("Obtain the home page list data")
        return try {
            val apiResponse = WanAndroidDataProvider.getArticleTopList()
            apiResponse.data
        } catch (e: Exception) {
            e.printStackTrace()
            arrayListOf()
        }
    }

}

class RefreshAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        // Clear the state to show loading screen
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs.clear()
        }
        ArticleListWidgetGlance().update(context, glanceId)
    }
}