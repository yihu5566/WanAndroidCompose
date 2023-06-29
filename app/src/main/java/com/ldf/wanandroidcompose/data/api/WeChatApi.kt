package com.ldf.wanandroidcompose.data.api

import com.ldf.wanandroidcompose.data.bean.ApiResponse
import com.ldf.wanandroidcompose.data.bean.Article
import com.ldf.wanandroidcompose.data.bean.Classify
import com.ldf.wanandroidcompose.data.bean.PageResponse
import retrofit2.http.*

/**
 * Http接口，Retrofit的请求Service
 *
 * @author LTP  2022/3/21
 */
interface WeChatApi {

    /** 获取公众号作者列表 */
    @GET("wxarticle/chapters/json")
    suspend fun getAuthorTitleList(): ApiResponse<List<Classify>>

    /** 获取公众号作者文章分页列表 */
    @GET("wxarticle/list/{authorId}/{pageNo}/json")
    suspend fun getAuthorArticlePageList(
        @Path("authorId") authorId: Int,
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>
}