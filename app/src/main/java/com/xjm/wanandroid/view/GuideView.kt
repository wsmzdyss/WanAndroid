package com.xjm.wanandroid.view

import com.xjm.wanandroid.base.BaseView
import com.xjm.wanandroid.bean.response.Guide

/**
 * Created by xjm on 2018/11/24.
 */
interface GuideView : BaseView {
    fun onGuideResult(t: List<Guide>)
}