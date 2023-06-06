package com.ldf.wanandroidcompose.data.bean

import com.ldf.wanandroidcompose.data.bean.Article

/**
 * 导航实体
 * @author LTP  2022/4/7
 */
data class Navigation(
    var articles: List<Article>,
    var cid: Int,
    var name: String
)