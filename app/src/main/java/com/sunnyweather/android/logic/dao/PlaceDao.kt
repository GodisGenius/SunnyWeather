package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place

object PlaceDao {

    //将place存储到Sharepreferences文件中
    fun savePlace(place : Place){
        //通过GSON将place对转换成JSON字符串
        sharePreferences().edit {
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavedPlace():Place{
        //读取字符串，通过GSON将JSON字符串解析成place对象
        val placeJson = sharePreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    //判断是否有数据已被存储
    fun isPlaceSaved() = sharePreferences().contains("place")

    private fun sharePreferences() = SunnyWeatherApplication.context.
            getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}