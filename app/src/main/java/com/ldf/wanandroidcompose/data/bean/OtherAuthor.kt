package com.btpj.lib_base.data.bean

import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.PageResponse

/**
 * 其他文章作者信息实体
 * @author LTP  2022/4/18
 */
data class OtherAuthor(
    val coinInfo: CoinInfo,
    val shareArticles: PageResponse<Article>
)