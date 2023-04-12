package com.sunnyweather.android.ui.place

import android.app.DownloadManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    //对界面上显示的城市数据进行缓存
    val placeList = ArrayList<Place>()

    //通过转换函数，调用仓库层中的searchPlace方法发起网络请求
    val placeLiveData = Transformations.switchMap(searchLiveData){query ->
        Repository.searchPlaces(query)
    }

    //将传入的搜索参数赋值给searchLiveData
    fun searchPlaces(query: String){
        searchLiveData.value = query
    }
}