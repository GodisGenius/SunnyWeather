package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

//全局获取Context的方式
class SunnyWeatherApplication : Application() {

    companion object{
        const val TOKEN = "xMLfJQX3mnZ5Qjk9"

        @SuppressLint("StaticFiledLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}