package com.yunlan.kotlinutil.http

import com.alibaba.fastjson.JSONObject
import com.orhanobut.logger.Logger
import com.yunlan.kotlinutil.http.exception.ServerException
import com.yunlan.kotlinutil.model.net.ResMessageBean
import com.yunlan.kotlinutil.model.net.ResultBean
import io.reactivex.ObservableTransformer
import okhttp3.ResponseBody

object RxUtils {


    fun  parseResult(json: String): ResultBean {
        val resMessageBean = JSONObject.parseObject(json, ResMessageBean::class.java)
        return JSONObject.parseObject(resMessageBean.svcContBean.result,ResultBean::class.java)
    }

    inline fun <reified T> rxTranslate2List(): ObservableTransformer<ResponseBody, ArrayList<T>> {
        return ObservableTransformer { it ->
            it.map {
                val resultBean = parseResult(it.string())
                Logger.json(resultBean.resultData)
                if (resultBean.resultCode == "SUCCESS") {
                    ArrayList(JSONObject.parseArray(resultBean.resultData,T::class.java))
                } else {
                    throw ServerException(resultBean.resultCode ?: "0", resultBean.resultMsg ?: "未知错误")
                }
            }
        }
    }

    inline fun <reified T> rxTranslate2Bean(): ObservableTransformer<ResponseBody, T> {
        return ObservableTransformer { it ->
            it.map {
                Logger.json(it.string())
                val resultBean = parseResult(it.string())
                Logger.json(resultBean.resultData)
                if (resultBean.resultCode == "SUCCESS") {
                    JSONObject.parseObject(resultBean.resultData,T::class.java)
                } else {
                    throw ServerException(resultBean.resultCode ?: "0", resultBean.resultMsg ?: "未知错误")
                }
            }
        }
    }

    fun rxTranslate2Msg(): ObservableTransformer<ResponseBody, String> {
        return ObservableTransformer { it ->
            it.map {
                Logger.json(it.string())
                val resultBean = parseResult(it.string())
                Logger.json(resultBean.resultData)
                if (resultBean.resultCode == "SUCCESS") {
                    resultBean.resultMsg
                } else {
                    throw ServerException(resultBean.resultCode ?: "0", resultBean.resultMsg ?: "未知错误")
                }
            }
        }
    }
}