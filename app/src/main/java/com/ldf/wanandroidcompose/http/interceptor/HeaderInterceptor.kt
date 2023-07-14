package com.ldf.wanandroidcompose.http.interceptor

import com.blankj.utilcode.BuildConfig
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.utils.LocalDataManage
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

/**
 * okhttp 日志拦截器
 * @author LTP  2022/3/21
 */
private const val SAVE_USER_LOGIN_KEY = "user/login"
private const val SAVE_USER_REGISTER_KEY = "user/register"
private const val SET_COOKIE_KEY = "set-cookie"
private const val COOKIE_NAME = "Cookie"

class HeaderInterceptor : Interceptor {
    override fun intercept(it: Interceptor.Chain): Response {
        val request = it.request()
        val builder = request.newBuilder()
        val response = it.proceed(request)
        val requestUrl = request.url.toString()
        val domain = request.url.host

        //cookie可能有多个，都保存下来
        if ((requestUrl.contains(SAVE_USER_LOGIN_KEY) || requestUrl.contains(
                SAVE_USER_REGISTER_KEY
            )) && response.headers(SET_COOKIE_KEY).isNotEmpty()
        ) {
            val cookies = response.headers(SET_COOKIE_KEY)
            val cookie = encodeCookie(cookies)
            saveCookie(requestUrl, domain, cookie)
        }

        //获取domain内的cookie
        if (domain.isNotEmpty()) {
            val sqDomain: String = LocalDataManage.readStringData(domain, "")
            val cookie: String = if (sqDomain.isNotEmpty()) sqDomain else ""
            if (cookie.isNotEmpty()) {
                builder.addHeader(COOKIE_NAME, cookie)
            }
        }
        return response
    }

    /**
     * 保存cookie
     */
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        LocalDataManage.putSyncData(url, cookies)
        domain ?: return
        LocalDataManage.putSyncData(domain, cookies)
    }

    /**
     * 整理cookie
     */
    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach { it ->
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }
}
