package com.xjm.wanandroid.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.xjm.wanandroid.common.AppManager
import com.xjm.wanandroid.common.BaseToolbarInterface

/**
 * Created by xjm on 2018/11/2.
 */
open class BaseActivity : RxAppCompatActivity() {

    val contentView: View
        get() {
            val content = findViewById<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

}