package com.yunlan.kotlinutil

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlin.properties.Delegates

class App : Application() {

    companion object {
        var instance: App by Delegates.notNull()

        val application: Application
            get() = instance

        val appContext: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
        Logger.addLogAdapter(object : AndroidLogAdapter(){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}