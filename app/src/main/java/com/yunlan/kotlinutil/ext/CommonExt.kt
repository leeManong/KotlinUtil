package com.yunlan.kotlinutil.ext

import android.arch.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.yunlan.kotlinutil.http.BaseObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.execute(lifecycleOwner: LifecycleOwner, baseObserver: BaseObserver<T>) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .`as`<ObservableSubscribeProxy<T>>(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
        .subscribe(baseObserver)
}

fun <T> Observable<ArrayList<T>>.executeArray(lifecycleOwner: LifecycleOwner, baseObserver: BaseObserver<ArrayList<T>>) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .`as`<ObservableSubscribeProxy<ArrayList<T>>>(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
        .subscribe(baseObserver)
}