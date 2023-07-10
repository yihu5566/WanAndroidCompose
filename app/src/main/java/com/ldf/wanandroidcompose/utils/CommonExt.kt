package com.ldf.wanandroidcompose.utils

import android.content.Context
import com.blankj.utilcode.util.ToastUtils

/**
 * @Author : dongfang
 * @Created Time : 2023-06-27  10:23
 * @Description:
 */

fun String.toast(context: Context) {
    ToastUtils.showLong(this)
}