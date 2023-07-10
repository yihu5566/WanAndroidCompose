package com.ldf.wanandroidcompose.utils.room

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

/**
 * @Author : dongfang
 * @Created Time : 2023-07-10  10:16
 * @Description:
 */
@Entity
data class HistorySearchKey(
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

@Dao
interface HistorySearchDao {
    @Query("SELECT * FROM HistorySearchKey")
    fun queryAll(): List<HistorySearchKey>?

    @Query("SELECT * FROM HistorySearchKey")
    fun queryAllForFlow(): Flow<List<HistorySearchKey>>

    //查，text是否存在，如果存在返回1，不存在返回0  limit 1只返回一条
    @Query("select count(*) from HistorySearchKey where title = :text limit 1")
    fun querySearchHistory(text: String): Int

    //增
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchHistory(searchHistoryData: HistorySearchKey)

    @Insert
    fun insertAll(vararg historyKeys: HistorySearchKey)

    @Insert
    fun insertAll(historyKey: HistorySearchKey)

    @Delete
    fun delete(historyKey: HistorySearchKey)

    @Query("delete from HistorySearchKey where id = :id")
    fun delete(id: Int?)
}

@Database(entities = [HistorySearchKey::class], version = 1/*, exportSchema = false*/)
abstract class SearchHistoryDB : RoomDatabase() {
    abstract fun historySearchDao(): HistorySearchDao

    companion object {
        //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序.
        @Volatile
        private var instance: SearchHistoryDB? = null

        //保证线程安全，锁住
        @Synchronized
        fun getInstance(context: Context): SearchHistoryDB =
            instance ?: Room.databaseBuilder(
                context,
                SearchHistoryDB::class.java,
                "HistorySearchKey"
            ).build()
                .also { instance = it }
    }
}

