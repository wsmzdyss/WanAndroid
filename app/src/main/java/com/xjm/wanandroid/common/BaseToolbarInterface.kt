package com.xjm.wanandroid.common

import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by xjm on 2018/11/12.
 */
interface BaseToolbarInterface {
    fun initToolbar(toolbar: Toolbar, center: TextView, right: TextView, back: ImageView)
}