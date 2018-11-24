package com.xjm.wanandroid.base

/**
 * Created by xjm on 2018/11/2.
 */
data class BaseResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)