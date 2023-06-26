package com.ldf.wanandroidcompose.ui.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.LogUtils
import com.ldf.wanandroidcompose.data.bean.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

/**
 * @Author : dongfang
 * @Created Time : 2023-06-25  14:11
 * @Description:
 */
object LocalDataManage {
    private lateinit var dataStore: DataStore<Preferences>
    private const val preferenceName = "PlayAndroidDataStore"
    private const val USER_NAME = "userName"
    private const val USER = "user"


    fun init(context: Context) {
        dataStore = context.createDataStore(preferenceName)
    }

    suspend fun saveLastUserName(userName: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey(USER_NAME)] = userName
        }
    }

    suspend fun saveUser(data: User) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey(USER)] = GsonUtils.toJson(data)
        }
    }

    suspend fun getUser(): Flow<User?> {
        LogUtils.d("----->>>获取用户信息")
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                LogUtils.d("----->>>"+it[preferencesKey(USER)])
                GsonUtils.fromJson(it[preferencesKey(USER)], User::class.java)
            }
    }


    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    fun clearSync() {
        runBlocking {
            dataStore.edit {
                it.clear()
            }
        }
    }
}