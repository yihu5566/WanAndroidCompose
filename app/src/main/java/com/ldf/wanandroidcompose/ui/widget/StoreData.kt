package com.ldf.wanandroidcompose.ui.widget

import androidx.lifecycle.MutableLiveData
import com.ldf.wanandroidcompose.data.bean.Classify
import com.ldf.wanandroidcompose.data.bean.ProjectTitle

/**
 * 存放临时数据
 */
object StoreData {

    //项目页面顶部指示器数据
    val projectTopBarListData = MutableLiveData<List<ProjectTitle>>()

    //公众号页面顶部指示器数据
    val publicNumTopBarListData = MutableLiveData<List<Classify>>()

}