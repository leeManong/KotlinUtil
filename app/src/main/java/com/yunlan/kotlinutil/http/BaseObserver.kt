package com.yunlan.kotlinutil.http

import com.orhanobut.logger.Logger
import com.yunlan.kotlinutil.http.exception.ApiException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T> : Observer<T> {

    abstract override fun onSubscribe(d: Disposable)

    abstract override fun onNext(t: T)

    override fun onError(e: Throwable) {
        if (e is ApiException) {
            Logger.e(e.message ?: "未知错误")
            onError(e)
        } else {
            Logger.e(e.message ?: "未知错误")
            onError(ApiException.handleException(e))
        }
    }

    abstract override fun onComplete()

    abstract fun onError(e: ApiException)
}