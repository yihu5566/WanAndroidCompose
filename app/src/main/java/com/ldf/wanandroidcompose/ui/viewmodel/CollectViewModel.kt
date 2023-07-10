package com.ldf.wanandroidcompose.ui.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.utils.CommonPagingSource
import kotlinx.coroutines.flow.Flow

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  15:09
 * @Description:
 */

class CollectViewModel : BaseViewModel() {



    override fun start() {

    }

    /** 请求首页轮播图 */
    fun collectArticle(id: Int) {
        launch({
            handleRequest(WanAndroidDataProvider.collectArticle(id), {
                LogUtils.d(it.errorMsg)
            }, {
                LogUtils.d(it.errorMsg)
                false
            })
        })
    }

    fun unCollectArticle(id: Int) {
        launch({
            handleRequest(WanAndroidDataProvider.unCollectArticle(id), {

            })
        })
    }

}