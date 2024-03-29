package com.ldf.wanandroidcompose.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.ldf.wanandroidcompose.utils.LocalDataManage
import kotlin.properties.Delegates

/**
 * Application基类
 *
 * @author LTP  2022/3/21
 */
class App : Application(), ViewModelStoreOwner {

    override lateinit var viewModelStore: ViewModelStore
    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        var appContext: Context by Delegates.notNull()

        lateinit var appViewModel: AppViewModel

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        viewModelStore = ViewModelStore()
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]
        LocalDataManage.init(appContext)
    }

    /** 获取一个全局的ViewModel */
    private fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}