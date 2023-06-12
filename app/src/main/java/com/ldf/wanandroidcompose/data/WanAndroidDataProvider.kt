package com.ldf.wanandroidcompose.data

import com.ldf.wanandroidcompose.data.bean.HotSearch
import com.ldf.wanandroidcompose.data.bean.OtherAuthor
import com.ldf.wanandroidcompose.data.bean.PageResponse
import com.ldf.wanandroidcompose.data.bean.ApiResponse
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.data.bean.ProjectTitle
import com.ldf.wanandroidcompose.data.http.Api
import com.ldf.wanandroidcompose.data.http.ProjectApi
import com.ldf.wanandroidcompose.http.BaseRepository
import com.ldf.wanandroidcompose.http.RetrofitManager

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  11:35
 * @Description:
 */
object WanAndroidDataProvider : BaseRepository(), Api, ProjectApi {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }
    private val serviceProjectApi by lazy { RetrofitManager.getService(ProjectApi::class.java) }

    override suspend fun getBanner(): ApiResponse<List<Banner>> {
        return apiCall { service.getBanner() }
    }

    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        return apiCall { service.getArticleTopList() }
    }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getArticlePageList(pageNo, pageSize) }
    }

    override suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.collectArticle(id) }
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectArticle(id) }
    }

    override suspend fun getOtherAuthorArticlePageList(
        id: Int,
        page: Int
    ): ApiResponse<OtherAuthor> {
        return apiCall { service.getOtherAuthorArticlePageList(id, page) }
    }

    override suspend fun getHotSearchList(): ApiResponse<List<HotSearch>> {
        return apiCall { service.getHotSearchList() }
    }

    override suspend fun getSearchDataByKey(
        pageNo: Int,
        searchKey: String
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getSearchDataByKey(pageNo, searchKey) }
    }

    override suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>> {
        return apiCall { serviceProjectApi.getProjectTitleList() }
    }

    override suspend fun getNewProjectPageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { serviceProjectApi.getNewProjectPageList(pageNo, pageSize) }
    }

    override suspend fun getProjectPageList(
        pageNo: Int,
        pageSize: Int,
        categoryId: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { serviceProjectApi.getProjectPageList(pageNo, pageSize, categoryId) }
    }
}