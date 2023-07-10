package com.ldf.wanandroidcompose.utils

import android.content.Context
import android.widget.Toast
import com.ldf.wanandroidcompose.ui.theme.Nav

/**
 * 点击两次返回按钮关闭app
 */
class TwoBackFinish {

    companion object {
        var mExitTime: Long = 0
    }

    fun execute(context: Context, finish:() -> Unit) {
            when {
                /**
                 * 点击两次退出程序 有事件间隔，间隔内退出程序，否则提示
                 */
                (System.currentTimeMillis() - mExitTime) > 1500 -> {
                    Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                    mExitTime = System.currentTimeMillis()
                }
                else -> {
                    Nav.twoBackFinishActivity = true
                    finish()
                }
            }
    }

}
