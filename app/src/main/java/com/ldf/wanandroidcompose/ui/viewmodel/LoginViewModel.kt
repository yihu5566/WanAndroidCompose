package com.ldf.wanandroidcompose.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldf.wanandroidcompose.base.App
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.WanAndroidDataProvider
import com.ldf.wanandroidcompose.data.bean.CoinInfo
import com.ldf.wanandroidcompose.data.bean.User
import com.ldf.wanandroidcompose.utils.CommonConstant
import com.ldf.wanandroidcompose.utils.LocalDataManage
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
                LocalDataManage.saveStringData(CommonConstant.USER, GsonUtils.toJson(it.data))
                App.appViewModel.userEvent.value = it.data
                successCall.invoke()
            })
        })
    }

    fun getUserInfoIntegral() {
        launch({
            handleRequest(WanAndroidDataProvider.getUserIntegral(), successBlock = {
                _userIntegralData.postValue(it.data)
            }, {
                _userIntegralData.postValue(null)
                false
            })
        })
    }


    fun getUserInfo() {
        launch({
            LocalDataManage.getData(CommonConstant.USER, "").map {
                val user = GsonUtils.fromJson(it, User::class.java)
                _userData.postValue(user)
            }
        })
    }

    /**
     * 用户注册
     */
    fun userRegister(userName: String, pwd: String, confirmPwd: String, successCall: () -> Unit) {
        if (userName == "") {
            ToastUtils.showLong("用户名不能为空")
        }

        if (pwd == "") {
            ToastUtils.showLong("密码不能为空")
        }
        if (confirmPwd == "") {
            ToastUtils.showLong("确认密码不能为空")
        }
        if (confirmPwd != pwd) {
            ToastUtils.showLong("密码和确认密码不一致")
        }
        launch({
            handleRequest(
                WanAndroidDataProvider.register(userName, pwd, confirmPwd),
                successBlock = {
                    successCall.invoke()
                },
                errorBlock = {
//                successCall.invoke()
                    ToastUtils.showLong(it.errorMsg)
                    false
                })
        })
    }
}