package com.xjm.wanandroid.base

/**
 * Created by xjm on 2018/11/2.
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text: String)
}