package com.ldf.wanandroidcompose.ui.home

import androidx.compose.runtime.mutableStateListOf
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.HomeDataProvider
import com.ldf.wanandroidcompose.data.bean.Banner

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  15:09
 * @Description:
 */

class HomeViewModel : BaseViewModel() {

    /** Banner列表 */
    val bannerListLiveData = mutableStateListOf<Banner>()

    override fun start() {
        fetchBanners()
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


}