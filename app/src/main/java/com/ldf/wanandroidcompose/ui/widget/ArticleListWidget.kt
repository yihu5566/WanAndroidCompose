package com.ldf.wanandroidcompose.ui.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * @Author : dongfang
 * @Created Time : 2023-09-15  11:23
 * @Description:
 */
class ArticleListWidget : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ArticleListWidgetGlance()

}