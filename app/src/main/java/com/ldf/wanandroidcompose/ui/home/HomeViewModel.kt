package com.ldf.wanandroidcompose.ui.home

import androidx.lifecycle.MutableLiveData
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
    val bannerListLiveData = MutableLiveData<List<Banner>>()
    override fun start() {

    }

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(HomeDataProvider.getBanner(), {
                bannerListLiveData.value = it.data
            })
        })
    }


}