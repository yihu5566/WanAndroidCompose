package com.ldf.wanandroidcompose.ui.project

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.data.bean.PageResponse
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.HomeDataProvider
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.ui.utils.CommonPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 *
 * @Author : dongfang
 * @Created Time : 2023/6/9  16:36
 * @Description:
 *
 */

class ProjectViewModel : BaseViewModel() {
    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    //首页列表状态
    val homeLazyListState: LazyListState = LazyListState()

    /** Banner列表 */
    val bannerListLiveData = mutableStateListOf<Banner>()

    //首页列表
    val homeListData: Flow<PagingData<Article>>
        get() = _homeListData

    private val _homeListData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        CommonPagingSource { nextPage: Int ->
            HomeDataProvider.getArticlePageList(nextPage, PAGE_SIZE).data
        }
    }.flow.cachedIn(viewModelScope)


    //置顶文章列表数据
    private val _articleTopList = MutableLiveData<List<Article>>()
    val articleTopList: LiveData<List<Article>>
        get() = _articleTopList

    override fun start() {
        fetchBanners()
    }

    fun sleepThreeTime() {

    }

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(HomeDataProvider.getBanner(), {
                bannerListLiveData.addAll(it.data)
                LogUtils.d(it.errorMsg)
            }, {
                LogUtils.d(it.errorMsg)
                false
            })
        })
    }

    fun fetchTopArticleList() {
        launch({
            handleRequest(HomeDataProvider.getArticleTopList(), {
                _articleTopList.postValue(it.data)
            })
        })
    }
}