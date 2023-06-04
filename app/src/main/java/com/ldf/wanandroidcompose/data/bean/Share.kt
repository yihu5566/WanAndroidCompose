package com.btpj.lib_base.data.bean

import com.ldf.wanandroidcompose.data.bean.Article

/**
 * 我的分享
 *
 * @author LTP  2022/4/13
 */
data class Share(
    var coinInfo: CoinInfo,
    var shareArticles: PageResponse<Article>
)