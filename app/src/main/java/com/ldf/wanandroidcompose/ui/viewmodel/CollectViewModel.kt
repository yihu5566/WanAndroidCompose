package com.ldf.wanandroidcompose.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldf.wanandroidcompose.base.App
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.User
import com.ldf.wanandroidcompose.utils.CommonConstant
import com.ldf.wanandroidcompose.utils.LocalDataManage
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