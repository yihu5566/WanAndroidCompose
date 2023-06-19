package com.ldf.wanandroidcompose.ui.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.test.internal.util.LogUtil
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.PageResponse
import com.ldf.wanandroidcompose.data.bean.ProjectTitle
import com.ldf.wanandroidcompose.ui.theme.Nav
import com.ldf.wanandroidcompose.ui.utils.CommonPagingSource
import com.ldf.wanandroidcompose.ui.widget.StoreData
import kotlinx.coroutines.flow.Flow

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
        const val PAGE_SIZE = 20
    }

    override fun start() {

    }

    //项目页面列表状态
    val projectLazyListState: LazyListState = LazyListState()

    //保存改变过index和offset的指示器Index
    var saveChangeProjectIndex = 0

    //选中分类的cid
    private val indexCid
        get() = StoreData.projectTopBarListData.value?.get(Nav.projectTopBarIndex.value)?.id ?: 0

    //项目页面顶部指示器
    private val _projectTreeData = MutableLiveData<List<ProjectTitle>>()
    val projectTreeData: LiveData<List<ProjectTitle>>
        get() = _projectTreeData

    /** 请求项目标题列表 */
    fun fetchProjectTitleList() {
        launch({
            handleRequest(
                WanAndroidDataProvider.getProjectTitleList(),
                {
                    _projectTreeData.postValue(it.data)
                    StoreData.projectTopBarListData.postValue(it.data)
                })
        })
    }

    //首页列表
    val projectListData: Flow<PagingData<Article>>
        get() = _projectListData

    private val _projectListData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        CommonPagingSource { nextPage: Int ->
            LogUtils.d("分页$nextPage===cid$indexCid")
            WanAndroidDataProvider.getProjectPageList(nextPage, PAGE_SIZE, indexCid).data
        }
    }.flow.cachedIn(viewModelScope)
}