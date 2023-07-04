package com.ldf.wanandroidcompose.data

import com.ldf.wanandroidcompose.data.bean.HotSearch
import com.ldf.wanandroidcompose.data.bean.OtherAuthor
import com.ldf.wanandroidcompose.data.bean.PageResponse
import com.ldf.wanandroidcompose.data.bean.ApiResponse
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Banner
import com.ldf.wanandroidcompose.data.bean.Classify
import com.ldf.wanandroidcompose.data.bean.CoinInfo
import com.ldf.wanandroidcompose.data.bean.CollectArticle
import com.ldf.wanandroidcompose.data.bean.CollectUrl
import com.ldf.wanandroidcompose.data.bean.IntegralRecord
import com.ldf.wanandroidcompose.data.bean.ProjectTitle
import com.ldf.wanandroidcompose.data.bean.Share
import com.ldf.wanandroidcompose.data.bean.User
import com.ldf.wanandroidcompose.data.api.Api
import com.ldf.wanandroidcompose.data.api.ProfileApi
import com.ldf.wanandroidcompose.data.api.ProjectApi
import com.ldf.wanandroidcompose.data.api.SquareApi
import com.ldf.wanandroidcompose.data.api.WeChatApi
import com.ldf.wanandroidcompose.data.bean.MySystem
import com.ldf.wanandroidcompose.data.bean.Navigation
import com.ldf.wanandroidcompose.http.BaseRepository
import com.ldf.wanandroidcompose.http.RetrofitManager

/**
 * @Author : dongfang
 * @Created Time : 2023-06-04  11:35
 * @Description:
 */
object WanAndroidDataProvider : BaseRepository(), Api, ProjectApi, WeChatApi, ProfileApi,SquareApi {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }
    private val serviceSquareApi by lazy { RetrofitManager.getService(SquareApi::class.java) }
    private val serviceProjectApi by lazy { RetrofitManager.getService(ProjectApi::class.java) }
    private val serviceWeChatApi by lazy { RetrofitManager.getService(WeChatApi::class.java) }
    private val serviceProfileApi by lazy { RetrofitManager.getService(ProfileApi::class.java) }

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

    override suspend fun getSquarePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { serviceSquareApi.getSquarePageList(pageNo, pageSize) }
    }

    override suspend fun getAskPageList(
        pageNo: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { serviceSquareApi.getAskPageList(pageNo) }
    }

    override suspend fun getTreeList(): ApiResponse<List<MySystem>> {
        return apiCall { serviceSquareApi.getTreeList() }
    }

    override suspend fun getNavigationList(): ApiResponse<List<Navigation>> {
        return apiCall { serviceSquareApi.getNavigationList() }
    }

    override suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.collectArticle(id) }
    }

    override suspend fun login(username: String, pwd: String): ApiResponse<User> {
        return apiCall { serviceProfileApi.login(username, pwd) }
    }

    override suspend fun logout(): ApiResponse<Any?> {
        return apiCall { serviceProfileApi.logout() }
    }

    override suspend fun register(
        username: String,
        pwd: String,
        pwdSure: String
    ): ApiResponse<Any?> {
        return apiCall { serviceProfileApi.register(username, pwd, pwdSure) }
    }

    override suspend fun getUserIntegral(): ApiResponse<CoinInfo> {
        return apiCall { serviceProfileApi.getUserIntegral() }
    }

    override suspend fun getIntegralRankPageList(pageNo: Int): ApiResponse<PageResponse<CoinInfo>> {
        return apiCall { serviceProfileApi.getIntegralRankPageList(pageNo) }
    }

    override suspend fun getIntegralRecordPageList(pageNo: Int): ApiResponse<PageResponse<IntegralRecord>> {
        return apiCall { serviceProfileApi.getIntegralRecordPageList(pageNo) }

    }

    override suspend fun getCollectUrlList(): ApiResponse<List<CollectUrl>> {
        return apiCall { serviceProfileApi.getCollectUrlList() }
    }

    override suspend fun getMyShareArticlePageList(pageNo: Int): ApiResponse<Share> {
        return apiCall { serviceProfileApi.getMyShareArticlePageList(pageNo) }
    }

    override suspend fun addArticle(title: String, link: String): ApiResponse<Any?> {
        return apiCall { serviceProfileApi.addArticle(title, link) }
    }

    override suspend fun deleteShareArticle(id: Int): ApiResponse<Any?> {
        return apiCall { serviceProfileApi.deleteShareArticle(id) }
    }

    override suspend fun unCollectUrl(id: Int): ApiResponse<Any?> {
        return apiCall { serviceProfileApi.unCollectUrl(id) }
    }

    override suspend fun getCollectArticlePageList(pageNo: Int): ApiResponse<PageResponse<CollectArticle>> {
        return apiCall { serviceProfileApi.getCollectArticlePageList(pageNo) }
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectArticle(id) }
    }

    override suspend fun getAuthorTitleList(): ApiResponse<List<Classify>> {
        return apiCall { serviceWeChatApi.getAuthorTitleList() }
    }

    override suspend fun getAuthorArticlePageList(
        authorId: Int,
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { serviceWeChatApi.getAuthorArticlePageList(authorId, pageNo, pageSize) }
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