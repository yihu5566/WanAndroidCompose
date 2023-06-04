package com.ldf.wanandroidcompose.data

import com.btpj.lib_base.data.bean.HotSearch
import com.btpj.lib_base.data.bean.OtherAuthor
import com.btpj.lib_base.data.bean.PageResponse
import com.ldf.wanandroidcompose.data.bean.ApiResponse
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.data.http.Api
import com.ldf.wanandroidcompose.http.BaseRepository
import com.ldf.wanandroidcompose.http.RetrofitManager

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  11:35
 * @Description:
 */
object HomeDataProvider : BaseRepository(), Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun getBanner(): ApiResponse<List<Banner>> {
        return apiCall { service.getBanner() }
    }

    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        TODO("Not yet implemented")
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        TODO("Not yet implemented")
    }

    override suspend fun getOtherAuthorArticlePageList(
        id: Int,
        page: Int
    ): ApiResponse<OtherAuthor> {
        TODO("Not yet implemented")
    }

    override suspend fun getHotSearchList(): ApiResponse<List<HotSearch>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchDataByKey(
        pageNo: Int,
        searchKey: String
    ): ApiResponse<PageResponse<Article>> {
        TODO("Not yet implemented")
    }
}