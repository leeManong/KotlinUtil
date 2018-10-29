package com.yunlan.kotlinutil.model.net

import com.alibaba.fastjson.annotation.JSONField

data class ResMessageBean(
    @JSONField(name = "SvcCont") val svcContBean: SvcContBean,
    @JSONField(name = "TcpCont") val tcpContBean: TcpContBean
)

data class SvcContBean(
    @JSONField(name = "PARAMS") var params: Any?,
    @JSONField(name = "result") val result: String? = null
)

data class TcpContBean(
    @JSONField(name = "ServiceCode") val serviceCode: String,
    @JSONField(name = "SrcSysID") val srcSysID: String,

    @JSONField(name = "SrcSysPassWord") val srcSysPassWord: String,
    @JSONField(name = "SrcSysSign") val srcSysSign: String,
    @JSONField(name = "TransactionID") val transactionID: String
)

data class ResultBean(
    val resultCode: String? = null,
    val resultMsg: String? = null,
    val resultData: String? = null
)