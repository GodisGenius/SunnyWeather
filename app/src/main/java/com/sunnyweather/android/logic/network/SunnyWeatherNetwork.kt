package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


//统一的网络数据源访问入口，对所有网络请求的API进行封装
object SunnyWeatherNetwork {

    //对WeatherService接口进行封装
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun getDailyWeather(lng:String,lat:String)=
        weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String,lat: String)=
        weatherService.getRealtimeWeather(lng, lat).await()

    //创建一个PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create<PlaceService>()

    //借助协程技术实现，将searchPlaces函数挂起
    suspend fun searchPlaces(query:String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if(body!=null)continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("respone body is null")
                    )
                    }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}