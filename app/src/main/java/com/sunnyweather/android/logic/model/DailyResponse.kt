package com.sunnyweather.android.logic.model
import java.util.*
import com.google.gson.annotations.SerializedName

//获取未来几天天气信息接口,返回的天气数据全部是数组形成
data class DailyResponse(val status:String ,val result: Result){

    data class Result(val daily:Daily)

    data class Daily(val temperature:List<Temperature>,val skycon:List<Skycon>,@SerializedName("life_index") val lifeIndex: LifeIndex)

    data class Temperature(val max:Float,val min:Float)

    data class Skycon(val value: String,val date:Date)

    data class LifeIndex(val coldRisk:List<LifeDescription>,val carWashing:List<LifeDescription>,val ultraviolet:List<LifeDescription>,val dressing:List<LifeDescription>)

    data class LifeDescription(val desc:String)

}
