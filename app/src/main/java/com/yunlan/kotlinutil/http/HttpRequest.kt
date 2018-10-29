package com.yunlan.kotlinutil.http

import android.arch.lifecycle.LifecycleOwner

object HttpRequest {

    fun post(interfaceName: String,lifecycleOwner: LifecycleOwner) = PostRequest(interfaceName,lifecycleOwner)
}