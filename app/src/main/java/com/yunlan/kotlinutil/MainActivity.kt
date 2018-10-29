package com.yunlan.kotlinutil

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yunlan.kotlinutil.http.HttpCallBack
import com.yunlan.kotlinutil.http.HttpRequest
import com.yunlan.kotlinutil.http.exception.ApiException
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HttpRequest.post("queryActivityRush",this)
            .params("rushCode" to "QIANGGOU02")
            .executeArray(object : HttpCallBack<RushBean>(){
                override fun onStart() {

                }

                override fun onSuccess(list: ArrayList<RushBean>) {
                    super.onSuccess(list)
                }

                override fun onError(e: ApiException) {
                    toast(e.msg ?: "未知错误")
                }

                override fun onCompleted() {

                }

            })
    }
}
