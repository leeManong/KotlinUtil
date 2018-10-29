package com.yunlan.kotlinutil

import com.google.gson.annotations.SerializedName

data class RushBean(
    @SerializedName("circleCode")
    val circleCode: String,
    @SerializedName("createDt")
    val createDt: Long,
    @SerializedName("describes")
    val describes: String,
    @SerializedName("endTime")
    val endTime: Long,
    @SerializedName("followCount")
    val followCount: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("linkPhone")
    val linkPhone: String,
    @SerializedName("merchantCode")
    val merchantCode: String,
    @SerializedName("merchantName")
    val merchantName: String,
    @SerializedName("normalPrice")
    val normalPrice: String,
    @SerializedName("num")
    val num: String,
    @SerializedName("payPrice")
    val payPrice: String,
    @SerializedName("picUrl")
    val picUrl: String,
    @SerializedName("rushCode")
    val rushCode: String,
    @SerializedName("rushName")
    val rushName: String,
    @SerializedName("seq")
    val seq: Int,
    @SerializedName("startTime")
    val startTime: Long,
    @SerializedName("status")
    val status: String,
    @SerializedName("surNum")
    val surNum: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("visitCount")
    val visitCount: String
)