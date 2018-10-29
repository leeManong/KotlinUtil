package com.yunlan.kotlinutil.http

import com.yunlan.kotlinutil.http.exception.ApiException

abstract class HttpCallBack<T> {

    abstract fun onStart()

    open fun onSuccess(bean: T) {

    }

    open fun onSuccess(list: ArrayList<T>) {

    }


    abstract fun onError(e: ApiException)

    abstract fun onCompleted()

}