package com.ldf.wanandroidcompose.data.api

import com.ldf.wanandroidcompose.data.bean.ApiResponse
import com.ldf.wanandroidcompose.data.bean.CoinInfo
import com.ldf.wanandroidcompose.data.bean.CollectArticle
import com.ldf.wanandroidcompose.data.bean.CollectUrl
import com.ldf.wanandroidcompose.data.bean.IntegralRecord
import com.ldf.wanandroidcompose.data.bean.PageResponse
import com.ldf.wanandroidcompose.data.bean.Share
import com.ldf.wanandroidcompose.data.bean.User
import retrofit2.http.*

/**
 * Http接口，Retrofit的请求Service
 *
 * @author LTP  2022/3/21
 */
interface ProfileApi {

    /** 登录 */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ): ApiResponse<User>

    /** 登出 */
    @GET("user/logout/json")
    suspend fun logout(): ApiResponse<Any?>

    /** 注册 */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") pwd: String,
        @Field("repassword") pwdSure: String
    ): ApiResponse<Any?>

    /** 获取个人积分 */
    @GET("lg/coin/userinfo/json")
    suspend fun getUserIntegral(): ApiResponse<CoinInfo>

    /** 获取积分排行列表分页 */
    @GET("coin/rank/{pageNo}/json")
    suspend fun getIntegralRankPageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<CoinInfo>>

    /** 获取积分历史 */
    @GET("lg/coin/list/{pageNo}/json")
    suspend fun getIntegralRecordPageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<IntegralRecord>>

    /** 获取收藏网址列表 */
    @GET("lg/collect/usertools/json")
    suspend fun getCollectUrlList(): ApiResponse<List<CollectUrl>>

    /** 获取我分享的文章分页列表 */
    @GET("user/lg/private_articles/{pageNo}/json")
    suspend fun getMyShareArticlePageList(@Path("pageNo") pageNo: Int): ApiResponse<Share>

    /** 添加要分享的文章 */
    @POST("lg/user_article/add/json")
    @FormUrlEncoded
    suspend fun addArticle(
        @Field("title") title: String,
        @Field("link") link: String
    ): ApiResponse<Any?>

    /** 删除自己分享的文章 */
    @POST("lg/user_article/delete/{id}/json")
    suspend fun deleteShareArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 取消收藏站内文章 */
    @POST("lg/collect/deletetool/json")
    suspend fun unCollectUrl(@Query("id") id: Int): ApiResponse<Any?>

    /** 获取收藏文章分页列表 */
    @GET("lg/collect/list/{pageNo}/json")
    suspend fun getCollectArticlePageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<CollectArticle>>

    /** 取消收藏站内文章 */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): ApiResponse<Any?>
}