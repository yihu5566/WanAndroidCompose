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
import com.ldf.wanandroidcompose.data.bean.Classify
import com.ldf.wanandroidcompose.data.bean.ProjectTitle
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.utils.CommonPagingSource
import com.ldf.wanandroidcompose.ui.widget.StoreData
import kotlinx.coroutines.flow.Flow

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  15:09
 * @Description:
 */

class PublicNumViewModel : BaseViewModel() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 20
    }

    override fun start() {

    }

    //项目页面列表状态
    val wechatLazyListState: LazyListState = LazyListState()

    //保存改变过index和offset的指示器Index
    var saveChangeWechatIndex = 0

    //选中分类的cid
    private val indexCid
        get() = StoreData.publicNumTopBarListData.value?.get(Nav.publicNumIndex.value)?.id ?: 408

    //公众号页面顶部指示器
    private val _wechatTreeData = MutableLiveData<List<Classify>>()
    val wechatTreeData: LiveData<List<Classify>>
        get() = _wechatTreeData

    /** 请求公众号标题列表 */
    fun fetchWechatTitleList() {
        launch({
            handleRequest(
                WanAndroidDataProvider.getAuthorTitleList(),
                {
                    _wechatTreeData.postValue(it.data)
                    StoreData.publicNumTopBarListData.postValue(it.data)
                })
        })
    }

    //首页列表
    val wechatArticleListData: Flow<PagingData<Article>>
        get() = _wechatListData

    private val _wechatListData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        CommonPagingSource { nextPage: Int ->
            LogUtils.d("分页$nextPage===cid$indexCid")
            WanAndroidDataProvider.getAuthorArticlePageList(indexCid, nextPage, PAGE_SIZE).data
        }
    }.flow.cachedIn(viewModelScope)


}