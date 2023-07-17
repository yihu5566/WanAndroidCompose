package com.ldf.wanandroidcompose.ui.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.CoinInfo
import com.ldf.wanandroidcompose.data.bean.CollectArticle
import com.ldf.wanandroidcompose.utils.CommonPagingSource
import kotlinx.coroutines.flow.Flow

/**
 *
 * @Author : dongfang
 * @Created Time : 2023/7/17  14:59
 * @Description: 积分排行
 *
 */

class IntegralRankListViewModel : BaseViewModel() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    val integralRankLazyListState: LazyListState = LazyListState()

    override fun start() {

    }

    //积分排行
    val integralRankListData: Flow<PagingData<CoinInfo>>
        get() = _integralRankListData

    private val _integralRankListData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        CommonPagingSource { nextPage: Int ->
            WanAndroidDataProvider.getIntegralRankPageList(nextPage).data
        }
    }.flow.cachedIn(viewModelScope)


}