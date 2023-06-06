package com.ldf.wanandroidcompose.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.data.bean.PageResponse
import com.ldf.wanandroidcompose.base.BaseViewModel
import com.ldf.wanandroidcompose.base.ext.handleRequest
import com.ldf.wanandroidcompose.base.ext.launch
import com.ldf.wanandroidcompose.data.HomeDataProvider
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  15:09
 * @Description:
 */

class HomeViewModel : BaseViewModel() {
    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    /** Banner列表 */
    val bannerListLiveData = mutableStateListOf<Banner>()

    val articlePageListLiveData = MutableLiveData<PageResponse<Article>>()
    val articleList =
        mutableStateOf<PageResponse<Article>>(PageResponse(0, listOf(), 0, false, 0, 0, 0))


    override fun start() {
        fetchBanners()
    }

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(HomeDataProvider.getBanner(), {
                bannerListLiveData.addAll(it.data)
                LogUtils.d(it.errorMsg)
            }, {
                LogUtils.d(it.errorMsg)
                false
            })
        })
    }

    fun fetchArticlePageList(pageNo: Int = 0) {
        launch({
            if (pageNo == 0) {
                // 使用async需要单独加作用域,不然没网时会崩溃
                withContext(Dispatchers.IO) {
                    // 第一页会同时请求置顶文章列表接口和分页文章列表的接口，使用async进行并行请求速度更快（默认是串行的）
                    val response1 = async { HomeDataProvider.getArticlePageList(pageNo, PAGE_SIZE) }
                    val response2 = async { HomeDataProvider.getArticleTopList() }

                    handleRequest(response1.await(), {
                        val list = response1.await()
                        handleRequest(response2.await(), {
                            (list.data.datas as ArrayList<Article>).addAll(
                                0,
                                response2.await().data
                            )
                            // 加了Dispatchers.IO现在是子线程,需要使用postValue的方式
                            articlePageListLiveData.postValue(list.data)
//                            articleList.value = list.data
                        })
                    })
                }
            } else {
                handleRequest(
                    HomeDataProvider.getArticlePageList(pageNo, PAGE_SIZE),
                    {
                        articlePageListLiveData.value = it.data
//                        articleList.value = it.data
                    })
            }
        })
    }


}