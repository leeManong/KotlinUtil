package com.yunlan.kotlinutil.ext

import com.alibaba.fastjson.JSONObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)

inline fun <reified T> Gson.fromJsonToArray(json: String): T = fromJson<T>(json, object : TypeToken<T>() {}.type)

