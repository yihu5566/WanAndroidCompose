package com.ldf.wanandroidcompose.ui.profile

import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.base.App
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.http.RetrofitManager
import com.ldf.wanandroidcompose.ui.utils.LocalDataManage

/**
 * @Author : dongfang
 * @Created Time : 2023-06-26  13:36
 * @Description:
 */
class SettingViewModel : BaseViewModel() {
    override fun start() {
    }

    fun logout(logoutSuccess: () -> Unit) {
        launch({
            handleRequest(WanAndroidDataProvider.logout(), successBlock = {
                LogUtils.d("---登出成功----")
                RetrofitManager.cookieJar.clear()
                App.appViewModel.userEvent.value = null
                LocalDataManage.clear()
                logoutSuccess.invoke()
            })
        })
    }
}