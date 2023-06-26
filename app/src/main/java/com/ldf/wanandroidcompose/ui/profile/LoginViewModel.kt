package com.ldf.wanandroidcompose.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldf.wanandroidcompose.base.App
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.CoinInfo
import com.ldf.wanandroidcompose.data.bean.User
import com.ldf.wanandroidcompose.http.RetrofitManager
import com.ldf.wanandroidcompose.ui.utils.LocalDataManage
import kotlinx.coroutines.flow.map

/**
 * @Author : dongfang
 * @Created Time : 2023-06-25  13:47
 * @Description:
 */
class LoginViewModel : BaseViewModel() {
    //积分信息
    private val _userIntegralData = MutableLiveData<CoinInfo>()
    val userIntegralData: LiveData<CoinInfo>
        get() = _userIntegralData

    //用户信息
    private val _userData = MutableLiveData<User?>()
    val userData: LiveData<User?>
        get() = _userData

    override fun start() {

    }

    fun userLogin(userName: String, pwd: String, successCall: () -> Unit) {
        if (userName == "") {
            ToastUtils.showLong("用户名不能为空")
        }

        if (pwd == "") {
            ToastUtils.showLong("密码不能为空")
        }
        launch({
            handleRequest(WanAndroidDataProvider.login(userName, pwd), successBlock = {
                LocalDataManage.saveLastUserName(userName)
                LocalDataManage.saveUser(it.data)
                App.appViewModel.userEvent.value = it.data
                successCall.invoke()
            }, errorBlock = {
//                successCall.invoke()
                ToastUtils.showLong(it.errorMsg)
                false
            })
        })
    }

    fun getUserInfoIntegral() {
        launch({
            handleRequest(WanAndroidDataProvider.getUserIntegral(), successBlock = {
                _userIntegralData.postValue(it.data)
            })
        })
    }



    fun getUserInfo() {
        launch({
            LocalDataManage.getUser().map {
                _userData.postValue(it)
            }
        })
    }
}