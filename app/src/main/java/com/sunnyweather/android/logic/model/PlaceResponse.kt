package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

//按照搜索城市数据接口返回的JSON格式定义的类和属性
class PlaceResponse(val status: String, val places: List<Place>)

//通过注释的方式让JSON字段和Kotlin字段之间建立映射关系
class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

class Location(val lng: String, val lat: String)
