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
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Classify
import com.ldf.wanandroidcompose.data.bean.MySystem
import com.ldf.wanandroidcompose.data.bean.Navigation
import com.ldf.wanandroidcompose.utils.CommonPagingSource
import com.ldf.wanandroidcompose.ui.widget.StoreData
import kotlinx.coroutines.flow.Flow

/**
 * @Author : dongfang
 * @Created Time : 2023-07-04  10:51
 * @Description:
 */
class SquareViewModel : BaseViewModel() {
    //广场
    val squareIndexState: LazyListState = LazyListState()

    //每日一问
    val questionIndexState: LazyListState = LazyListState()

    //体系
    val systemIndexState: LazyListState = LazyListState()

    //导航
    val naviIndexState: LazyListState = LazyListState()
    override fun start() {

    }

    //广场列表
    val squareArticleData: Flow<PagingData<Article>>
        get() = _squareArticleData

    private val _squareArticleData = Pager(PagingConfig(pageSize = PublicNumViewModel.PAGE_SIZE)) {
        CommonPagingSource { nextPage: Int ->
            LogUtils.d("分页$nextPage===")
            WanAndroidDataProvider.getSquarePageList(nextPage, PublicNumViewModel.PAGE_SIZE).data
        }
    }.flow.cachedIn(viewModelScope)

    //问答列表
    val askArticleData: Flow<PagingData<Article>>
        get() = _askArticleData

    private val _askArticleData = Pager(PagingConfig(pageSize = PublicNumViewModel.PAGE_SIZE)) {
        CommonPagingSource { nextPage: Int ->
            LogUtils.d("分页$nextPage===")
            WanAndroidDataProvider.getAskPageList(nextPage).data
        }
    }.flow.cachedIn(viewModelScope)


    //体系
    private val _systemData = MutableLiveData<List<MySystem>>()
    val systemTreeData: LiveData<List<MySystem>>
        get() = _systemData

    //获取体系
    fun fetchTreeList() {
        launch({
            handleRequest(
                WanAndroidDataProvider.getTreeList(),
                {
                    _systemData.postValue(it.data)
                })
        })
    }

    //导航
    private val _navigationData = MutableLiveData<List<Navigation>>()
    val navigationTreeData: LiveData<List<Navigation>>
        get() = _navigationData

    //获取体系
    fun fetchNavigationList() {
        launch({
            handleRequest(
                WanAndroidDataProvider.getNavigationList(),
                {
                    _navigationData.postValue(it.data)
                })
        })
    }
}