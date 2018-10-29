package com.yunlan.kotlinutil.http

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("unite/service")
    fun request(@Body param: RequestBody): Observable<ResponseBody>
}
