package com.ldf.wanandroidcompose.ui.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.CollectArticle
import com.ldf.wanandroidcompose.data.bean.Share
import com.ldf.wanandroidcompose.utils.CommonPagingSource
import kotlinx.coroutines.flow.Flow

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  15:09
 * @Description:我分享的文章
 */

class ShareArticlesListViewModel : BaseViewModel() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    val shareLazyListState: LazyListState = LazyListState()

    override fun start() {

    }

    val shareListData: Flow<PagingData<Article>>
        get() = _shareListData

    private val _shareListData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        CommonPagingSource { nextPage: Int ->
            WanAndroidDataProvider.getMyShareArticlePageList(nextPage).data.shareArticles
        }
    }.flow.cachedIn(viewModelScope)


}