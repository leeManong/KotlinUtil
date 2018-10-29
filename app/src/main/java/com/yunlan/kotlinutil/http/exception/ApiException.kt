package com.yunlan.kotlinutil.http.exception

import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class ApiException(private val throwable: Throwable, val code: Int) : Exception(throwable) {

    var msg: String? = throwable.message

    companion object {

        //对应HTTP的状态码
        private val BADREQUEST = 400
        private val UNAUTHORIZED = 401
        private val FORBIDDEN = 403
        private val NOT_FOUND = 404
        private val METHOD_NOT_ALLOWED = 405
        private val REQUEST_TIMEOUT = 408
        private val INTERNAL_SERVER_ERROR = 500
        private val BAD_GATEWAY = 502
        private val SERVICE_UNAVAILABLE = 503
        private val GATEWAY_TIMEOUT = 504


        fun handleException(e: Throwable): ApiException {
            val ex: ApiException
            when (e) {
                is HttpException -> {
                    ex = ApiException(e, e.code())
                    ex.msg = "网络错误"
                    return ex

                }
                is ServerException -> {
                    ex = ApiException(e, ERROR.SERVER_EXCEPTION)
                    ex.msg = e.message
                    return ex
                }
                is JsonParseException -> {
                    ex = ApiException(e, ERROR.PARSE_ERROR)
                    ex.msg = "解析错误"
                    return ex
                }
                is ClassCastException -> {
                    ex = ApiException(e, ERROR.CAST_ERROR)
                    ex.msg = "类型转换错误"
                    return ex
                }
                is ConnectException -> {
                    ex = ApiException(e, ERROR.NETWORD_ERROR)
                    ex.msg = "连接失败"
                    return ex
                }
                is javax.net.ssl.SSLHandshakeException -> {
                    ex = ApiException(e, ERROR.SSL_ERROR)
                    ex.msg = "证书验证失败"
                    return ex
                }
                is ConnectTimeoutException -> {
                    ex = ApiException(e, ERROR.TIMEOUT_ERROR)
                    ex.msg = "连接超时"
                    return ex
                }
                is java.net.SocketTimeoutException -> {
                    ex = ApiException(e, ERROR.TIMEOUT_ERROR)
                    ex.msg = "连接超时"
                    return ex
                }
                is UnknownHostException -> {
                    ex = ApiException(e, ERROR.UNKNOWNHOST_ERROR)
                    ex.msg = "无法解析该域名"
                    return ex
                }
                is NullPointerException -> {
                    ex = ApiException(e, ERROR.NULLPOINTER_EXCEPTION)
                    ex.msg = "NullPointerException"
                    return ex
                }
                else -> {
                    ex = ApiException(e, ERROR.UNKNOWN)
                    ex.msg = "未知错误"
                    return ex
                }
            }
        }
    }

    object ERROR {
        /**
         * 未知错误
         */
        val UNKNOWN = 1000
        /**
         * 解析错误
         */
        val PARSE_ERROR = UNKNOWN + 1
        /**
         * 网络错误
         */
        val NETWORD_ERROR = PARSE_ERROR + 1
        /**
         * 协议出错
         */
        val HTTP_ERROR = NETWORD_ERROR + 1

        /**
         * 证书出错
         */
        val SSL_ERROR = HTTP_ERROR + 1

        /**
         * 连接超时
         */
        val TIMEOUT_ERROR = SSL_ERROR + 1

        /**
         * 调用错误
         */
        val INVOKE_ERROR = TIMEOUT_ERROR + 1
        /**
         * 类转换错误
         */
        val CAST_ERROR = INVOKE_ERROR + 1
        /**
         * 请求取消
         */
        val REQUEST_CANCEL = CAST_ERROR + 1
        /**
         * 未知主机错误
         */
        val UNKNOWNHOST_ERROR = REQUEST_CANCEL + 1

        /**
         * 空指针错误
         */
        val NULLPOINTER_EXCEPTION = UNKNOWNHOST_ERROR + 1


        val SERVER_EXCEPTION = NULLPOINTER_EXCEPTION + 1
    }
}