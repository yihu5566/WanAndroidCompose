package com.ldf.wanandroidcompose.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.data.bean.PageResponse
import java.lang.Exception

/**
 * @Author : dongfang
 * @Created Time : 2023-06-07  11:46
 * @Description:
 */
class CommonPagingSource<T : Any>(private val block: suspend (nextPage: Int) -> PageResponse<T>) :
    PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            //params.key为当前页码 页码从1开始
            val nextPage = params.key ?: 1
            //更新页码后请求数据
            val response = block.invoke(nextPage)
            LogUtils.d("数据刷新---》${response.curPage}")
            LoadResult.Page(
                data = response.datas,
                //前一页页码
                prevKey = if (nextPage == 0) null else nextPage - 1,
                //后一页页码
                nextKey = if (nextPage < response.pageCount) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}