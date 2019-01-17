package com.xjm.wanandroid.base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.xjm.wanandroid.common.AppManager
import com.xjm.wanandroid.utils.Constant
import com.xjm.wanandroid.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 * Created by xjm on 2018/11/2.
 */
open class BaseActivity : RxAppCompatActivity() {

    protected var isLogin : Boolean by Preference(Constant.IS_LOGIN, false)

    protected var username : String by Preference(Constant.USERNAME, "")

    protected var isEventBusRegister: Boolean = false

    val contentView: View
        get() {
            val content = findViewById<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isEventBusRegister) {
            EventBus.getDefault().register(this)
        }
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isEventBusRegister) {
            EventBus.getDefault().unregister(this)
        }
        AppManager.instance.finishActivity(this)
    }

}