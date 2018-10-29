package com.yunlan.kotlinutil.http

import com.blankj.utilcode.util.NetworkUtils
import com.yunlan.kotlinutil.BuildConfig
import com.yunlan.kotlinutil.http.cookies.CookiesManager
import com.yunlan.kotlinutil.utils.AppConstants
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://circle.zgjuma.com/"

    val service: ApiServices by lazy (LazyThreadSafetyMode.SYNCHRONIZED){
        getRetrofit().create(ApiServices::class.java)
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(FastJsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient{
        val okHttpClient: OkHttpClient
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(loggingInterceptor)
        }
        val cacheFile = File(AppConstants.PATH_CACHE)
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())

        val cacheInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtils.isConnected()) {
                val maxAge = 0
                // 有网络时, 不缓存, 最大保存时长为0
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .removeHeader("Pragma")
                    .build()
            } else {
                // 无网络时，设置超时为4周
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            response
        }
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor)
        builder.addInterceptor(cacheInterceptor)
        builder.cache(cache)
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        //cookie认证
        builder.cookieJar(CookiesManager())
        okHttpClient = builder.build()
        return okHttpClient
    }
}