package com.ldf.wanandroidcompose.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.ldf.wanandroidcompose.data.bean.User

/**
 * App全局ViewModel可直接替代EventBus
 *
 * @author LTP  2022/4/13
 */
class AppViewModel : BaseViewModel() {
    override fun start() {}

    /** 全局用户 */
    val userEvent = MutableLiveData<User?>()


}