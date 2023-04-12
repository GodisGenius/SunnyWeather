package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

//仓库层的统一封装入口
object Repository {

    //
    fun searchPlaces(query: String) = liveData(Dispatchers.IO){     //线程参数，代码块中的所有代码都会运行在子线程当中
        val result = try {
            //调用searchPlaces搜索城市数据
            val placeRespone = SunnyWeatherNetwork.searchPlaces(query)
            //判断服务器状态
            if (placeRespone.status == "ok"){
                val places = placeRespone.places
                //包装获取的城市数据列表
                Result.success(places)
            }else {
                //包装一个异常信息
                Result.failure(RuntimeException("respone status is ${placeRespone.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        //将包装结果发射出去
        emit(result)
    }
}