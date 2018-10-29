package com.yunlan.kotlinutil.http

import android.arch.lifecycle.LifecycleOwner
import com.alibaba.fastjson.JSONObject
import com.orhanobut.logger.Logger
import com.yunlan.kotlinutil.ext.execute
import com.yunlan.kotlinutil.ext.executeArray
import com.yunlan.kotlinutil.http.exception.ApiException
import com.yunlan.kotlinutil.model.net.SvcContBean
import com.yunlan.kotlinutil.model.net.TcpContBean
import io.reactivex.disposables.Disposable
import okhttp3.RequestBody
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.set

class PostRequest(private val interfaceName: String, var lifecycleOwner: LifecycleOwner) {

    private var params: HashMap<String, Any> = HashMap()

    fun params(pair: Pair<String, Any>): PostRequest {
        params[pair.first] = pair.second
        return this
    }

    fun params(param: HashMap<String, Any>): PostRequest {
        params = param
        return this
    }

    fun createRequestBody(): RequestBody {
        val s = composeRequestMessage(interfaceName, params)
        Logger.json(s)
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), s)
    }

    inline fun <reified T> execute(callBack: HttpCallBack<T>): PostRequest {
        RetrofitClient.service.request(createRequestBody())
            .compose(RxUtils.rxTranslate2Bean<T>())
            .execute(lifecycleOwner, object : BaseObserver<T>() {
                override fun onSubscribe(d: Disposable) {
                    callBack.onStart()
                }

                override fun onNext(t: T) {
                    callBack.onSuccess(t)
                }

                override fun onComplete() {
                    callBack.onCompleted()
                }

                override fun onError(e: ApiException) {
                    callBack.onError(e)
                }


            })
        return this
    }

    inline fun <reified T> executeArray(callBack: HttpCallBack<T>): PostRequest {
        RetrofitClient.service.request(createRequestBody())
            .compose(RxUtils.rxTranslate2List<T>())
            .executeArray(lifecycleOwner, object : BaseObserver<ArrayList<T>>() {
                override fun onSubscribe(d: Disposable) {
                    callBack.onStart()
                }

                override fun onNext(t: ArrayList<T>) {
                    callBack.onSuccess(t)
                }

                override fun onComplete() {
                    callBack.onCompleted()
                }

                override fun onError(e: ApiException) {
                    callBack.onError(e)
                }
            })
        return this
    }

    fun executeMsg(callBack: HttpCallBack<String>): PostRequest {
        RetrofitClient.service.request(createRequestBody())
            .compose(RxUtils.rxTranslate2Msg())
            .execute(lifecycleOwner, object : BaseObserver<String>() {
                override fun onSubscribe(d: Disposable) {
                    callBack.onStart()
                }

                override fun onNext(t: String) {
                    callBack.onSuccess(t)
                }

                override fun onComplete() {
                    callBack.onCompleted()
                }

                override fun onError(e: ApiException) {
                    callBack.onError(e)
                }
            })
        return this
    }

    private fun composeRequestMessage(serviceName: String, params: Any): String {
        val tcpContBean = TcpContBean(
            srcSysID = "1003",
            serviceCode = serviceName,
            srcSysPassWord = "1003",
            srcSysSign = "123456",
            transactionID = "1322017119413"
        )
        val svContLst = ArrayList<SvcContBean>()
        val svcCont = SvcContBean(params)
        svContLst.add(svcCont)
        val map = HashMap<String, Any>()
        map["SvcCont"] = svContLst
        map["TcpCont"] = tcpContBean
        return JSONObject.toJSONString(map)
    }


}