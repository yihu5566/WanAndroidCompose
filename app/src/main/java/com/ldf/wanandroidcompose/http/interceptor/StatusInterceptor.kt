package com.ldf.wanandroidcompose.http.interceptor

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParser
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * @Author : dongfang
 * @Created Time : 2023/6/7  09:22
 * @Description:
 */
class StatusInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder().build()
        val response: Response = chain.proceed(request)

        if (!NetworkUtils.isAvailable()) {
            return response
        }
        // 拦截请求，获取到该次请求的request
        val path = request.url.toUrl().path
        // 执行本次网络请求操作，返回response信息
        val data = response.body?.string()
        var jsonstr: String?
        if (request.method == "POST") {
            jsonstr = data
            val sb = StringBuilder()
            if (request.body is FormBody) {
                val body = request.body as FormBody?
                for (i in 0 until body!!.size) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                }
                sb.delete(sb.length - 1, sb.length)
            }
            LogUtils.d(
                "请求方式:Post 请求连接:${request.url} 请求头:${request.headers} 请求参数$sb 请求结果: $jsonstr"
            )
        } else {
            jsonstr = data
            LogUtils.d(
                " 请求方式:Get 请求连接:${request.url} 请求头:${request.headers} 请求结果:$jsonstr"
            )
        }
        try {
            val code = JsonParser().parse(jsonstr).asJsonObject["errorCode"].asInt
            if (code == 401) {
                ToastUtils.showLong("账户失效，请重新登录")
                return response
            } else if (code == 260001 || code == 260002) {
                ToastUtils.showLong("账户过期，请重新登录")
                return response
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return if (request.method == "POST") {
            response.newBuilder()
                .body(
                    ResponseBody.create(
                        "application/json; charset=utf-8".toMediaTypeOrNull(),
                        jsonstr ?: ""
                    )
                )
                .build()
        } else response.newBuilder()
            .body(
                ResponseBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    data ?: ""
                )
            )
            .build()
    }
}