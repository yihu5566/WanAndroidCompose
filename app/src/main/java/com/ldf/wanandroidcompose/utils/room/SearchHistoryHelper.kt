package com.ldf.wanandroidcompose.utils.room

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 搜索历史帮助类
 */
object SearchHistoryHelper {

    /**
     * 获取room数据库中存储的搜索历史数据
     * 以普通数据对象形式返回
     */
    fun getSearchHistoryAllForFlow(context: Context) =
        SearchHistoryDB.getInstance(context).historySearchDao().queryAllForFlow()

    /**
     * 获取room数据库中存储的搜索历史数据
     * 以普通数据对象形式返回
     */
    fun getSearchHistoryAll(context: Context) =
        SearchHistoryDB.getInstance(context).historySearchDao().queryAll()

    /**
     * 查询text是否存在，存在返回1，不存在返回0，只返回一条
     */
    fun getSearchHistory(context: Context, text: String) =
        SearchHistoryDB.getInstance(context).historySearchDao().querySearchHistory(text)

//    /**
//     * 获取room数据库中存储的搜索历史数据
//     * 以LiveData形式返回
//     */
//    fun getLiveDataAllSearchHistory(context: Context): LiveData<List<SearchHistoryData>> =
//        SearchHistoryDB.getInstance(context).historySearchDao().queryLiveDataAll()
//
//
//    /**
//     * 删除room数据库中所有的搜索历史数据
//     */
//    fun CoroutineScope.deleteAll(context: Context) {
//        launch(Dispatchers.IO) {
//            getSearchHistoryAll(context)?.let {
//                SearchHistoryDB.getInstance(context).historySearchDao().deleteAll()
//            }
//        }
//    }

    /**
     * 删除某一项，根据id
     */
    fun CoroutineScope.delete(context: Context, id: Int?) {
        launch(Dispatchers.IO) {
            SearchHistoryDB.getInstance(context).historySearchDao().delete(id)
        }
    }

    /**
     * 新增搜索历史数据
     */
    fun CoroutineScope.insertSearchHistory(context: Context, searchHistoryData: HistorySearchKey) {
        launch(Dispatchers.IO) {

            //查询某个元素是否存在 如果存在的话不用保存
            val isExist = getSearchHistory(context, searchHistoryData.title)
            if (isExist == 1) return@launch

            val check = getSearchHistoryAll(context) ?: return@launch

            if (check.size >= 10) {
                //删除第一项
                check.first().id?.let { delete(context, it) }
            }

            //新增数据
            SearchHistoryDB.getInstance(context).historySearchDao()
                .insertSearchHistory(searchHistoryData)
        }
    }

}