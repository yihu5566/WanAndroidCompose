package com.ldf.wanandroidcompose.data.api

import com.ldf.wanandroidcompose.data.bean.ApiResponse
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.MySystem
import com.ldf.wanandroidcompose.data.bean.Navigation
import com.ldf.wanandroidcompose.data.bean.PageResponse
import retrofit2.http.*

/**
 * Http接口，Retrofit的请求Service
 *
 * @author LTP  2022/3/21
 */
interface SquareApi {

    /** 获取广场列表分页数据 */
    @GET("user_article/list/{pageNo}/json")
    suspend fun getSquarePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>

    /** 获取每日一问列表分页数据 */
    @GET("wenda/list/{pageNo}/json")
    suspend fun getAskPageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<Article>>

    /** 获取体系数据 */
    @GET("tree/json")
    suspend fun getTreeList(): ApiResponse<List<MySystem>>

    /** 获取导航数据 */
    @GET("navi/json")
    suspend fun getNavigationList(): ApiResponse<List<Navigation>>
}