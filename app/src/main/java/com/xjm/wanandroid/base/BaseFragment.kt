package com.xjm.wanandroid.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxFragment
import com.xjm.wanandroid.utils.Constant
import com.xjm.wanandroid.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 * Created by xjm on 2018/11/13.
 */
open class BaseFragment : RxFragment() {
    protected var isLogin : Boolean by Preference(Constant.IS_LOGIN, false)

    protected val username : String by Preference(Constant.USERNAME, "")

    protected var isEventBusRegister : Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isEventBusRegister) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isEventBusRegister) {
            EventBus.getDefault().unregister(this)
        }
    }
}