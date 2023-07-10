package com.ldf.wanandroidcompose.ui.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.base.App
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.HotSearch
import com.ldf.wanandroidcompose.data.bean.MySystem
import com.ldf.wanandroidcompose.http.RetrofitManager
import com.ldf.wanandroidcompose.utils.CommonConstant
import com.ldf.wanandroidcompose.utils.CommonPagingSource
import com.ldf.wanandroidcompose.utils.LocalDataManage
import kotlinx.coroutines.flow.Flow

/**
 * @Author : dongfang
 * @Created Time : 2023-06-26  13:36
 * @Description:
 */
class SearchViewModel : BaseViewModel() {

    //列表状态
    val searchLazyListState: LazyListState = LazyListState()

    //热门搜索
    private val _hostSearchData = MutableLiveData<List<HotSearch>>()
    val hostSearchTreeData: LiveData<List<HotSearch>>
        get() = _hostSearchData

    //关键字
    val searchKey = MutableLiveData<String>()

    //关键字搜索
    private val _keySearchData = Pager(PagingConfig(pageSize = PublicNumViewModel.PAGE_SIZE)) {
        CommonPagingSource { nextPage: Int ->
            LogUtils.d("---关键词搜索----" + searchKey.value)
            WanAndroidDataProvider.getSearchDataByKey(nextPage, searchKey.value ?: "").data
        }
    }.flow.cachedIn(viewModelScope)

    val keySearchData: Flow<PagingData<Article>>
        get() = _keySearchData

    override fun start() {
    }

    fun getHotSearchList() {
        launch({
            handleRequest(WanAndroidDataProvider.getHotSearchList(), successBlock = {
                LogUtils.d("---获取热门搜索词----")
                _hostSearchData.postValue(it.data)
            })
        })
    }
}