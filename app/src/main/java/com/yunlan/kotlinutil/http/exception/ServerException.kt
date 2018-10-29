package com.yunlan.kotlinutil.http.exception

import java.lang.RuntimeException

data class ServerException(val errCode: String, val msg: String) : RuntimeException(msg)