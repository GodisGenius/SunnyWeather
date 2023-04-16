package com.sunnyweather.android.logic.model

import android.app.DownloadManager
import com.google.gson.annotations.SerializedName

//获取实时天气信息接口相应的数据模型，防止出现和其它接口的数学模型类有同名冲突
data class RealtimeResponse(val status:String,val result: Result){
    data class Result(val realtime: Realtime)

    data class Realtime(val skycon:String,val temperature:Float,@SerializedName("air_quality") val airQuality: AirQuality)

    data class AirQuality(val aqi:AQI)

    data class AQI(val chn:Float)
}
