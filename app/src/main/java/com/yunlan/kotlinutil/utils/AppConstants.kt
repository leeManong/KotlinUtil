package com.yunlan.kotlinutil.utils

import com.yunlan.kotlinutil.App
import java.io.File

object AppConstants {

    val PATH_CACHE = App.application.cacheDir.absolutePath + File.separator + "data"
}