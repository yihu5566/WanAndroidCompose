package com.ldf.wanandroidcompose.ui.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldf.wanandroidcompose.base.App
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.data.bean.User
import com.ldf.wanandroidcompose.ui.profile.LoginViewModel
import com.ldf.wanandroidcompose.utils.CommonConstant
import com.ldf.wanandroidcompose.utils.CommonPagingSource
import com.ldf.wanandroidcompose.utils.LocalDataManage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  15:09
 * @Description:
 */

class CollectViewModel : BaseViewModel() {
    //用户信息
    private val _userData = MutableLiveData<User?>()
    val userData: LiveData<User?>
        get() = _userData

    override fun start() {
        launch({
            LocalDataManage.getData(CommonConstant.USER, "").map {
                val user = GsonUtils.fromJson(it, User::class.java)
                _userData.postValue(user)
                App.appViewModel.userEvent.postValue(user)
            }
        })
    }

    /** 请求首页轮播图 */
    fun collectArticle(id: Int) {
        if (userData.value == null) {
            ToastUtils.showLong("请先登录")
            return
        }

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
        if (userData.value == null) {
            ToastUtils.showLong("请先登录")
            return
        }
        launch({
            handleRequest(WanAndroidDataProvider.unCollectArticle(id), {

            })
        })
    }

}