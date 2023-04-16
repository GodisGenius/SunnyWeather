package com.sunnyweather.android.logic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

//仓库层的统一封装入口
object Repository {


    fun searchPlaces(query: String) = fire(Dispatchers.IO){     //线程参数，代码块中的所有代码都会运行在子线程当中
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
        //将包装结果发射出去
//        emit(result)
    }

    fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO){
            coroutineScope {
                //在子协程中发起网络请求
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    //在得到响应之后将realtime和daily对象取出并封装到一个Weather对象中
                    val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                    //封装weather
                    Result.success(weather)
                }else{
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" + "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}